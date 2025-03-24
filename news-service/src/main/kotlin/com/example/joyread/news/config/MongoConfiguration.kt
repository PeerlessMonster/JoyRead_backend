package com.example.joyread.news.config

import com.example.joyread.news.repository.converter.NewsContentConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfiguration {
    @Bean
    fun customConversions(): MongoCustomConversions {
        val converters = listOf(NewsContentConverter())
        return MongoCustomConversions(converters)
    }
}
