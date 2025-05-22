package com.example.joyread.assistant.autoconfigure

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(AIProperties::class)
class AIAutoConfiguration(
    private val aiProperties: AIProperties
) {
    @Bean
    fun aiClient(webClientBuilder: WebClient.Builder) = with(aiProperties) {
        webClientBuilder.baseUrl(baseURL)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
            .build()
    }
}
