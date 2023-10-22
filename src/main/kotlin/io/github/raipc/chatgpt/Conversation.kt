package io.github.raipc.chatgpt

import io.github.raipc.chatgpt.http.HttpResponse

interface Conversation {
    suspend fun sendMessage(message: InputMessage): HttpResponse
}