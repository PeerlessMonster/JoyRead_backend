package com.example.joyread.assistant.handler

import com.example.joyread.common.constant.HTTPMediaType
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow

@Component
class AIHandler(
    private val aiClient: WebClient
) {
    suspend fun sendChatMessages(body: Any) = aiClient.post()
        .uri("/chat-messages")
        .contentType(HTTPMediaType.APPLICATION_JSON_UTF8)
        .bodyValue(body)
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve()
        .bodyToFlow<JsonNode>()
}
