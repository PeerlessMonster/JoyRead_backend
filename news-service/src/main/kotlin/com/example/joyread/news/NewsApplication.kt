package com.example.joyread.news

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class NewsApplication

fun main(args: Array<String>) {
    runApplication<NewsApplication>(*args)
}
