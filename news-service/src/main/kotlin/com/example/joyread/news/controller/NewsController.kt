package com.example.joyread.news.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news")
class NewsController {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello from news-service"
    }
}
