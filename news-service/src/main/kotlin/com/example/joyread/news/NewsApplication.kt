package com.example.joyread.news

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication(scanBasePackages = ["com.example.joyread.common", "com.example.joyread.news"])
@EnableReactiveMongoRepositories
class NewsApplication

fun main(args: Array<String>) {
    runApplication<NewsApplication>(*args)
}
