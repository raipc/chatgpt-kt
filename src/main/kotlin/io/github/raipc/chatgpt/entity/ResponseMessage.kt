package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseMessage (
    override val id: String,
    val author: Author,
    val recipient: String,
    val status: String,
    @SerialName("end_turn")
    val endTurn: Boolean? = null,
    val weight: Double,
    @SerialName("update_time")
    val updateTime: String?,
    @SerialName("create_time")
    val createTime: String?,
    val content: Content,
    val metadata: Metadata
): Message