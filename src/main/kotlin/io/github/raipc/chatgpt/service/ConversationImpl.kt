package io.github.raipc.chatgpt.service

import io.github.raipc.chatgpt.UserInput
import io.github.raipc.chatgpt.Conversation
import io.github.raipc.chatgpt.ModelOutput
import io.github.raipc.chatgpt.entity.*
import io.github.raipc.chatgpt.entity.Content
import io.github.raipc.chatgpt.entity.Message
import io.github.raipc.chatgpt.entity.Request
import io.github.raipc.chatgpt.entity.RequestMessage
import io.github.raipc.chatgpt.http.HttpResponse
import java.util.UUID

internal class ConversationImpl(
    private val service: ConversationService,
    private val model: String,
    private val initPrompt: UserInput?
): Conversation {
    private val messageHistory: MutableList<Message> = mutableListOf()
    private var conversationId: String? = null

    override suspend fun sendMessage(message: UserInput): ModelOutput {
        val messages = mutableListOf<RequestMessage>()
        if (initPrompt != null && messageHistory.isEmpty()) {
            messages.add(initPrompt.toJsonMessage("system"))
        }
        messages.add(message.toJsonMessage("user"))
        val parentMsgId = messageHistory.lastOrNull()?.id ?: UUID.randomUUID().toString()
        val request = Request(
            model = model,
            messages = messages,
            parentMessageId = parentMsgId,
            conversationId = conversationId
        )
        val httpResponse = service.sendMessage(request)
        if (httpResponse.code != 200) {
            return ModelOutput("", httpResponse.body)
        }
        val responseParser = ResponseParser(httpResponse.body)
        val response = responseParser.parse()
        return response.toModelOutput().also {
            messageHistory.add(response.message)
            conversationId = response.conversationId
        }
    }

    private fun UserInput.toJsonMessage(role: String): RequestMessage {
        return RequestMessage(Content("text", listOf(content)), role)
    }

    private fun Response.toModelOutput(): ModelOutput {
        val content = this.message.content.parts.joinToString("\n")
        return ModelOutput(content, error)
    }
}