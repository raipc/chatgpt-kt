package io.github.raipc.chatgpt

data class UserInput(val content: String, val images: List<ByteArray> = emptyList())