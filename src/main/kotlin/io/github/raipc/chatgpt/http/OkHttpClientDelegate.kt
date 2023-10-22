package io.github.raipc.chatgpt.http

import io.github.oshai.kotlinlogging.KotlinLogging
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.gildor.coroutines.okhttp.await
import java.net.URL

private val logger = KotlinLogging.logger {}
internal class OkHttpClientDelegate(private val client: OkHttpClient): HttpClient {
    override suspend fun post(url: URL, headers: List<Pair<String, String>>, body: ByteArray, contentType: String): HttpResponse {
        val request = Request.Builder()
            .url(url)
            .apply { headers.forEach { addHeader(it.first, it.second) } }
            .post(body.toRequestBody(contentType = contentType.toMediaType()))
            .build()
        return doRequest(request)
    }

    override suspend fun get(url: URL, headers: List<Pair<String, String>>): HttpResponse {
        val request = Request.Builder()
            .url(url)
            .apply { headers.forEach { addHeader(it.first, it.second) } }
            .get()
            .build()
        return doRequest(request)
    }

    private suspend fun doRequest(request: Request): HttpResponse {
        logger.trace { "Request: $request" }
        val response = client.newCall(request).await()
        logger.trace { "Response: $response" }
        return HttpResponse(response.code, response.headers.toList(), response.body?.string() ?: "")
    }
}