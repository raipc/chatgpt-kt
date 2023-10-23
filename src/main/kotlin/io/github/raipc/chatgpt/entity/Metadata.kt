package io.github.raipc.chatgpt.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Metadata(
    @SerialName("finish_details")
    val finishDetails: FinishDetails? = null,
    @SerialName("is_complete")
    val isComplete: Boolean? = null,
    @SerialName("message_type")
    val messageType: String? = null,
    @SerialName("model_slug")
    val modelSlug: String? = null,
    @SerialName("parent_id")
    val parentId: String? = null,
    @SerialName("timestamp_")
    val timestamp: String? = null
)