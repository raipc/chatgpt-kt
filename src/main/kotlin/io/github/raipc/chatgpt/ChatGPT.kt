package io.github.raipc.chatgpt

import io.github.raipc.chatgpt.http.HttpClient
import io.github.raipc.chatgpt.service.ConversationImpl
import io.github.raipc.chatgpt.service.ConversationService

class ChatGPT internal constructor(
    sessionToken: String,
    cfClearance: String,
    userAgent: String,
    httpClient: HttpClient
) {
    private val conversationService = ConversationService(httpClient, cfClearance, sessionToken, userAgent)

    fun createConversation(model: String, prompt: InputMessage? = null): Conversation {
        return ConversationImpl(conversationService, model, prompt)
    }
}
