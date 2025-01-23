package com.example.joyread.user.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello from user-service"
    }
}
