package com.example.joyread.news.service

import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.repository.NewsDetailRepository
import com.example.joyread.news.util.asLatestNewsVO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsDetailRepository: NewsDetailRepository
) {
    suspend fun latest(pageOrder: Int, pageSize: Int): List<LatestNewsVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val newsList = mutableListOf<LatestNewsVO>()
        newsDetailRepository.findByOrderByPublishTimeDesc(page).collect { news ->
            newsList.add(news.asLatestNewsVO())
        }
        return newsList
    }
}
