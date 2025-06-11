package com.example.joyread.news.controller

import com.example.joyread.common.constant.HTTPMediaTypeValue
import com.example.joyread.common.domain.dto.NewsDetailDTO
import com.example.joyread.news.domain.vo.NewsDetailVO
import com.example.joyread.news.domain.vo.NewsVO
import com.example.joyread.common.domain.vo.NewsTitleVO
import com.example.joyread.news.service.NewsDetailService
import com.example.joyread.news.service.NewsService
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.validator.constraints.Length
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.Period

@RestController
@RequestMapping("/news", produces = [HTTPMediaTypeValue.APPLICATION_JSON_UTF8])
class NewsController(
    private val newsDetailService: NewsDetailService,
    private val newsService: NewsService
) {
    @GetMapping("/latest")
    suspend fun getLatestNews(
        @RequestParam @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @RequestParam @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<NewsDetailVO>> {
        val newsList = newsDetailService.latest(pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/popular/day")
    suspend fun getTodayPopularNews(
        @RequestParam @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @RequestParam @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<NewsTitleVO>> {
        val startTime = Instant.now() - Period.ofDays(60)

        val newsList = newsDetailService.popular(startTime, pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/popular/week")
    suspend fun getThisWeekPopularNews(
        @RequestParam @PositiveOrZero(message = "须为从 0 开始的整数") pageOrder: Int = 0,
        @RequestParam @Positive(message = "须为正整数") pageSize: Int
    ): ResponseEntity<List<NewsTitleVO>> {
        val startTime = Instant.now() - Period.ofDays(180)

        val newsList = newsDetailService.popular(startTime, pageOrder, pageSize)
        return ResponseEntity.ok(newsList)
    }

    @GetMapping("/{id}/detail")
    suspend fun getNewsDetail(
        @PathVariable("id") @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") id: String
    ): ResponseEntity<NewsDetailDTO> {
        val news = newsDetailService.read(id)
        return if (news != null) {
            ResponseEntity.ok(news)
        } else {
            ResponseEntity.notFound().build<NewsDetailDTO>()
        }
    }

    @GetMapping("/{id}")
    suspend fun getNews(
        @PathVariable("id") @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") id: String
    ): ResponseEntity<NewsVO> {
        val news = newsService.read(id)
        return if (news != null) {
            ResponseEntity.ok(news)
        } else {
            ResponseEntity.notFound().build<NewsVO>()
        }
    }
}
