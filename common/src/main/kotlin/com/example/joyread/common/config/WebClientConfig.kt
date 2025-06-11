package com.example.joyread.common.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @LoadBalanced
    @Bean
    fun loadBalancedWebClientBuilder() = WebClient.builder()

    @Bean
    fun webClientBuilder() = WebClient.builder()
}
