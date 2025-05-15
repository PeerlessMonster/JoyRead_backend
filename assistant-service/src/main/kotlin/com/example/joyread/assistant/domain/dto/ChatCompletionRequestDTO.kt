package com.example.joyread.assistant.domain.dto

import com.example.joyread.assistant.serializer.NullObjectSerializer
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonSerialize(nullsUsing = NullObjectSerializer::class)
class ChatCompletionRequestDTO(
    val user: String,
    val query: String,

    docId: String? = null
) {
    val inputs = if (docId == null) {
        null
    } else {
        Inputs(docId)
    }

    val responseMode = "streaming"
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Inputs(
    val docId: String
)
