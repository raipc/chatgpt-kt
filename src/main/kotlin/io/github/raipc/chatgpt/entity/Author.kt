package io.github.raipc.chatgpt.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
internal data class Author(
    val role: String,
    val name: String?,
    val metadata: Map<String, @Contextual Any>
)