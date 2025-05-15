package com.example.joyread.assistant

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.joyread.common", "com.example.joyread.assistant"])
class AssistantApplication

fun main(args: Array<String>) {
    runApplication<AssistantApplication>(*args)
}
