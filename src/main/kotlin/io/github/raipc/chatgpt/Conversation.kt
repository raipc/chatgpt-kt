package io.github.raipc.chatgpt

interface Conversation {
    suspend fun sendMessage(message: UserInput): ModelOutput
}