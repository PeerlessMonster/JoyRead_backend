package com.example.joyread.search.config.autoconfigure

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(DifyProperties::class)
class DifyAutoConfiguration(
    private val difyProperties: DifyProperties
) {
    @Bean
    fun knowledgeClient(webClientBuilder: WebClient.Builder) = with(difyProperties) {
        val datasetURL = "$baseURL/datasets/${dataset.id}"
        webClientBuilder.baseUrl(datasetURL)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${dataset.apiKey}")
            .build()
    }
}
