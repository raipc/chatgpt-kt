package io.github.raipc.chatgpt

import java.net.http.HttpClient

class ChatGPT(
    private val sessionToken: String,
    private val accessToken: String,
    private val cfClearance: String,
    private val userAgent: String,
    httpClientSetup: (HttpClient.Builder) -> Unit = {}

) {
    private val httpClient = HttpClient.newBuilder()
        .apply(httpClientSetup)
        .build()
    fun doStuff() {

    }
}
