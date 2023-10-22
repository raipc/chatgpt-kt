package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Response(
    var code: Int,
    val error: String,
    @SerialName("conversation_id")
    val conversationId: String,
    val message: ResponseMessage
)