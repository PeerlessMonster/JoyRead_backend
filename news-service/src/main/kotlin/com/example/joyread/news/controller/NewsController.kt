package com.example.joyread.news.controller

import com.example.joyread.common.constant.MediaType
import com.example.joyread.news.domain.po.LatestNewsPO
import com.example.joyread.news.service.NewsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news", produces = [MediaType.APPLICATION_JSON_UTF8_CHARSET])
class NewsController(private val newsService: NewsService) {
    @GetMapping("/latest")
    suspend fun sendLatest(pageOrder: Int = 0, pageSize: Int): ResponseEntity<List<LatestNewsPO>> {
        val newsList = newsService.latest(pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }
}
