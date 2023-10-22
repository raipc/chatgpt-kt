package io.github.raipc.chatgpt.entity

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
internal data class Message(
    val content: Content,
    val role: String = "user",
    val id: String = UUID.randomUUID().toString()
)