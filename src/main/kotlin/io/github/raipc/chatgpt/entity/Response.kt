package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Response(
    val error: String?,
    @SerialName("conversation_id")
    override val conversationId: String,
    val message: ResponseMessage
): ConversationPayload