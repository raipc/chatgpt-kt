package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FinishDetails(
    val type: String,
    @SerialName("stop_tokens")
    val stopTokens: List<Int>
)