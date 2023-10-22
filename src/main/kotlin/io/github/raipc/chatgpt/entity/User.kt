package io.github.raipc.chatgpt.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val picture: String = "",
    val groups: List<String> = emptyList(),
    val features: List<String> = emptyList()
)