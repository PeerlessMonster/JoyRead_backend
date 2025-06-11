package com.example.joyread.search.config.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.dify")
class DifyProperties(
    host: String,
    port: UShort,
    val dataset: Dataset
) {
    data class Dataset(
        val id: String,
        val apiKey: String
    )

    val baseURL = "http://$host:$port/v1"
}
