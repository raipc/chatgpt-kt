package io.github.raipc.chatgpt.service

import io.github.raipc.chatgpt.InputMessage
import io.github.raipc.chatgpt.Conversation
import io.github.raipc.chatgpt.entity.Content
import io.github.raipc.chatgpt.entity.Message
import io.github.raipc.chatgpt.entity.Request
import io.github.raipc.chatgpt.http.HttpResponse
import java.util.UUID

internal class ConversationImpl(
    private val service: ConversationService,
    private val model: String,
    private val initPrompt: InputMessage?
): Conversation {
    private val messageHistory: MutableList<Message> = mutableListOf()
    private var parentMsgId: String? = null
    private var conversationId: String? = null

    override suspend fun sendMessage(message: InputMessage): HttpResponse {
        val newMessage = message.toJsonMessage("user")
        val messages = if (initPrompt != null && parentMsgId == null)
            listOf(
                initPrompt.toJsonMessage("system"),
                newMessage
            ) else listOf(newMessage)
        val parent = parentMsgId ?: UUID.randomUUID().toString()
        val request = Request(
            model = model,
            messages = messages,
            parentMessageId = parent,
            conversationId = conversationId
        )
        return service.sendMessage(request)
    }

    private fun InputMessage.toJsonMessage(role: String): Message {
        return Message(Content("text", listOf(content)), role)
    }
}