package io.github.raipc.chatgpt.service

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object JsonCodec {
    private val jsonCodec = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    internal inline fun <reified T : Any> decodeFromString(value: String): T {
        return jsonCodec.decodeFromString<T>(value)
    }

    internal inline fun <reified T : Any> encodeToString(value: T): String {
        return jsonCodec.encodeToString(jsonCodec.serializersModule.serializer(), value)
    }
}