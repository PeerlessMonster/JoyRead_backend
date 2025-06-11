package com.example.joyread.search.handler

import com.example.joyread.common.constant.HTTPMediaType
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class DifyHandler(
    private val knowledgeClient: WebClient
) {
    suspend fun retrieveKnowledge(body: Any) = knowledgeClient.post()
        .uri("/retrieve")
        .contentType(HTTPMediaType.APPLICATION_JSON_UTF8)
        .bodyValue(body)
        .accept(HTTPMediaType.APPLICATION_JSON_UTF8)
        .retrieve()
        .awaitBody<JsonNode>()
}
