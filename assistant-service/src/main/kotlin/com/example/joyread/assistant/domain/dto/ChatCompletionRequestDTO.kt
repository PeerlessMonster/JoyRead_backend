package com.example.joyread.assistant.domain.dto

import com.example.joyread.assistant.serializer.NullObjectSerializer
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonSerialize(nullsUsing = NullObjectSerializer::class)
class ChatCompletionRequestDTO(
    val inputs: Inputs?,
    val query: String,
    val responseMode: String,
    val user: String
) {
    companion object {
        fun streaming(user: String, query: String, docId: String? = null): ChatCompletionRequestDTO {
            val inputs = if (docId == null) {
                null
            } else {
                Inputs(docId)
            }

            return ChatCompletionRequestDTO(inputs, query, "streaming", user)
        }
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Inputs(
    val docId: String
)
