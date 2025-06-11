package com.example.joyread.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.joyread.common", "com.example.joyread.search"])
class SearchApplication

fun main(args: Array<String>) {
    runApplication<SearchApplication>(*args)
}
