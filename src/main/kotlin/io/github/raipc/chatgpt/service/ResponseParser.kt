package io.github.raipc.chatgpt.service

import io.github.raipc.chatgpt.entity.Response

internal class ResponseParser(private val input: String) {
    var done: Boolean = false
    var moderations = mutableListOf<String>()
    private var lastOutput = ""

    fun parse(): Response {
        input.lineSequence()
            .filter { it.isNotBlank() }
            .forEach { line ->
                if ("data: [DONE]" == line) {
                    done = true
                } else if (line.contains("moderation_response")) {
                    moderations.add(line)
                } else {
                    JsonCodec.decodeFromString<Response>(line.substring(6))
                    lastOutput = line
                }
            }
        return lastOutput.let { JsonCodec.decodeFromString<Response>(it.substring(6)) }
    }
}