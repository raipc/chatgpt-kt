package io.github.raipc.chatgpt

import io.github.raipc.chatgpt.http.OkHttpClientDelegate
import okhttp3.OkHttpClient

object OkChatGPT {
    fun ChatGPT(
        sessionToken: String,
        cfClearance: String,
        userAgent: String,
        httpClientSetup: (OkHttpClient.Builder) -> Unit = {}
    ) = ChatGPT(sessionToken, cfClearance, userAgent, OkHttpClientDelegate(
        OkHttpClient().newBuilder().apply(httpClientSetup).build()
    ))
}