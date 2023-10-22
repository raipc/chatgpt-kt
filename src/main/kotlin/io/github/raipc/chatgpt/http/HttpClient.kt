package io.github.raipc.chatgpt.http

import java.net.URL

internal interface HttpClient {
    suspend fun post(url: URL, headers: List<Pair<String, String>>, body: ByteArray, contentType: String): HttpResponse
    suspend fun get(url: URL, headers: List<Pair<String, String>>): HttpResponse
}