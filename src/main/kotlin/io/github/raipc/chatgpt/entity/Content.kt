package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Content(
    @SerialName("content_type")
    val contentType: String,
    val parts: List<String>
)