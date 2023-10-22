package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseMessage(
    val id: String,
    val recipient: String,
    @SerialName("end_turn")
    val endTurn: String,
    val weight: Float,
    val role: String,
    val user: User,
    @SerialName("update_time")
    val updateTime: String,
    @SerialName("create_time")
    val createTime: String,
    val metadata: List<String>,
    val content: Content,
)