package com.example.joyread.search.config

import com.example.joyread.search.service.NewsDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class ServiceProxyConfig {
    @Bean
    fun newsDetailService(loadBalancedWebClientBuilder: WebClient.Builder): NewsDetailService {
        val webClient = loadBalancedWebClientBuilder.baseUrl("lb://news-service").build()
        val adapter = WebClientAdapter.create(webClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(NewsDetailService::class.java)
    }
}
