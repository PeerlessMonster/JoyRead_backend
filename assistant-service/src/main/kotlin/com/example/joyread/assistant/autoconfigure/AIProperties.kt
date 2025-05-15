package com.example.joyread.assistant.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.ai")
data class AIProperties(
    val baseUrl: String,
    val apiKey: String
)
