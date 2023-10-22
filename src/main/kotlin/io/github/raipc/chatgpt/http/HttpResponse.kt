package io.github.raipc.chatgpt.http

data class HttpResponse(val code: Int, val headers: List<Pair<String, String>>, val body: String)