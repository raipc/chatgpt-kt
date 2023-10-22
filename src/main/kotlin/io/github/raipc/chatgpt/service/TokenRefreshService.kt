package io.github.raipc.chatgpt.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.raipc.chatgpt.entity.AuthSession
import io.github.raipc.chatgpt.http.HttpClient
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

private val SERVICE_URL = URL("https://chat.openai.com/api/auth/session")
private val logger = KotlinLogging.logger {}

internal class TokenRefreshService(
    private val httpClient: HttpClient,
    private val cfClearanceToken: String,
    private val sessionToken: String,
    private val userAgent: String,
    private val clock: Clock = Clock.systemUTC(),
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
) {
    private val accessTokenFuture: AtomicReference<CompletableFuture<AuthSession>> = AtomicReference()

    suspend fun getAccessToken(): String {
        if (accessTokenFuture.compareAndSet(null, CompletableFuture())) {
            accessTokenFuture.get().complete(refresh())
        }
        return accessTokenFuture.get().thenApply { it.accessToken }.await()
    }

    private fun scheduleNextRefresh(authSession: AuthSession) {
        val expirationTime = Instant.parse(authSession.expires)
        val nextRefreshTime = expirationTime.minus(5, ChronoUnit.MINUTES)
        logger.debug { "Scheduled token refresh at $nextRefreshTime" }
        val delayMillis = Duration.between(clock.instant(), nextRefreshTime).toMillis()
        scheduler.schedule(this::updateToken, delayMillis, TimeUnit.MILLISECONDS)
    }

    private fun updateToken() {
        runBlocking {
            accessTokenFuture.set(CompletableFuture<AuthSession>().apply { complete(refresh()) })
        }
     }

    private suspend fun refresh(): AuthSession {
        logger.debug { "Refreshing token" }
        val headers = listOf(
            "Cookie" to "cf_clearance=$cfClearanceToken;__Secure-next-auth.session-token=$sessionToken",
            "User-Agent" to userAgent
        )
        val response = httpClient.get(SERVICE_URL, headers)
        if (response.code != 200) {
            throw IllegalStateException("Could not refresh access token. Please, try to provide another cfClearanceToken or sessionToken")
        }
        return JsonCodec.decodeFromString<AuthSession>(response.body)
            .also { scheduleNextRefresh(it) }
    }
}