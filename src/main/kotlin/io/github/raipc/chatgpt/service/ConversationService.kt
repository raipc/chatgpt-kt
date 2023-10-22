package io.github.raipc.chatgpt.service

import io.github.raipc.chatgpt.entity.Request
import io.github.raipc.chatgpt.http.HttpClient
import io.github.raipc.chatgpt.http.HttpResponse
import java.net.URL

private val SERVICE_URL = URL("https://chat.openai.com/backend-api/conversation")
internal class ConversationService(
    private val httpClient: HttpClient,
    private val cfClearanceToken: String,
    sessionToken: String,
    private val userAgent: String
) {
    private val tokenRefreshService = TokenRefreshService(httpClient, cfClearanceToken, sessionToken, userAgent)

    suspend fun sendMessage(request: Request): HttpResponse {
        val accessToken = tokenRefreshService.getAccessToken()
        val body = JsonCodec.encodeToString(request)
        val headers = listOf(
            "Authorization" to "Bearer $accessToken",
            "Accept" to "text/event-stream",
            "User-Agent" to userAgent,
            "Cookie" to "cf_clearance=$cfClearanceToken"
        )
        return httpClient.post(SERVICE_URL, headers, body.toByteArray(), "application/json")
    }
}