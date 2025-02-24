package com.example.joyread.news.service

import com.example.joyread.news.domain.po.LatestNewsPO
import com.example.joyread.news.repository.LatestNewsRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val latestNewsRepository: LatestNewsRepository
) {
    suspend fun latest(pageOrder: Int, pageSize: Int): List<LatestNewsPO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val newsList = mutableListOf<LatestNewsPO>()
        latestNewsRepository.findAllBy(page).collect(newsList::add)
        return newsList
    }
}
