package io.github.raipc.chatgpt.http

internal data class HttpResponse(val code: Int, val headers: List<Pair<String, String>>, val body: String)