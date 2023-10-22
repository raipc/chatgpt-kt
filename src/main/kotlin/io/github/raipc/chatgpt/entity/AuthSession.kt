package io.github.raipc.chatgpt.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class AuthSession(
   val user: User,
   val expires: String,
   val accessToken: String
)