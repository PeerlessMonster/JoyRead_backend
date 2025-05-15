package com.example.joyread.assistant.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider

class NullObjectSerializer : JsonSerializer<Any>() {
    companion object {
        private val objectMapper by lazy {
            ObjectMapper()
        }
    }

    override fun serialize(value: Any, gen: JsonGenerator, serializers: SerializerProvider) {
        val objectNode = objectMapper.createObjectNode()
        gen.writeObject(objectNode)
    }
}
