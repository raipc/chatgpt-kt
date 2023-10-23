package io.github.raipc.chatgpt.entity

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
internal data class RequestMessage(
    val content: Content,
    val role: String = "user",
    override val id: String = UUID.randomUUID().toString()
): Message