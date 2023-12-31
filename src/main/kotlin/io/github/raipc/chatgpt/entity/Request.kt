package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Request(
    val model: String = "text-davinci-002-render",
    val messages: List<RequestMessage>,
    @SerialName("parent_message_id")
    val parentMessageId: String? = null,
    @SerialName("conversation_id")
    override val conversationId: String? = null,
    val action: String = "next",
): ConversationPayload