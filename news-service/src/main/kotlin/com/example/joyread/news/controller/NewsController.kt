package com.example.joyread.news.controller

import com.example.joyread.common.constant.MediaType
import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.domain.vo.NewsVO
import com.example.joyread.news.domain.vo.PopularNewsVO
import com.example.joyread.news.service.NewsDetailService
import com.example.joyread.news.service.NewsService
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.validator.constraints.Length
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.Period

@RestController
@RequestMapping("/news", produces = [MediaType.APPLICATION_JSON_UTF8_CHARSET])
class NewsController(
    private val newsDetailService: NewsDetailService,
    private val newsService: NewsService
) {
    @GetMapping("/latest")
    suspend fun getLatestNews(
        @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<LatestNewsVO>> {
        val newsList = newsDetailService.latest(pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/popular/day")
    suspend fun getTodayPopularNews(
        @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<PopularNewsVO>> {
        val startTime = Instant.now() - Period.ofDays(1)

        val newsList = newsDetailService.popular(startTime, pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/popular/week")
    suspend fun getThisWeekPopularNews(
        @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<PopularNewsVO>> {
        val startTime = Instant.now() - Period.ofWeeks(1)

        val newsList = newsDetailService.popular(startTime, pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/{id}")
    suspend fun getNews(
        @PathVariable("id") @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") id: String
    ): ResponseEntity<NewsVO> {
        val news = newsService.content(id)
        return if (news != null) {
            ResponseEntity.ok(news)
        } else {
            ResponseEntity.notFound().build<NewsVO>()
        }
    }
}
