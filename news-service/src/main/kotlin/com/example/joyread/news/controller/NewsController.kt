package com.example.joyread.news.controller

import com.example.joyread.common.constant.MediaType
import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.domain.vo.NewsVO
import com.example.joyread.news.service.NewsService
import org.hibernate.validator.constraints.Length
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/news", produces = [MediaType.APPLICATION_JSON_UTF8_CHARSET])
class NewsController(private val newsService: NewsService) {
    @GetMapping("/latest")
    suspend fun getLatestNews(pageOrder: Int = 0, pageSize: Int): ResponseEntity<List<LatestNewsVO>> {
        val newsList = newsService.latest(pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/{id}")
    suspend fun getNews(
        @PathVariable("id") @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") id: String
    ): ResponseEntity<NewsVO> {
        val news = newsService.article(id)
        return if (news != null) {
            ResponseEntity.ok(news)
        } else {
            ResponseEntity.notFound().build<NewsVO>()
        }
    }

}
