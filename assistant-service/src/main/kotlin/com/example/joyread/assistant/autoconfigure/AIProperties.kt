package com.example.joyread.assistant.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.ai")
data class AIProperties(
    val baseURL: String,
    val apiKey: String
)
