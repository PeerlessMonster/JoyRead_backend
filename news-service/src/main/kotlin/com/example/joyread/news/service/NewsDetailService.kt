package com.example.joyread.news.service

import com.example.joyread.news.domain.vo.NewsDetailVO
import com.example.joyread.common.domain.vo.NewsTitleVO
import com.example.joyread.news.repository.NewsDetailRepository
import com.example.joyread.news.util.asLatestNewsVO
import com.example.joyread.news.util.asPopularNewsVO
import com.example.joyread.news.util.asNewsDetailDTO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NewsDetailService(
    private val newsDetailRepository: NewsDetailRepository
) {
    suspend fun read(id: String) = newsDetailRepository.findById(id)?.asNewsDetailDTO()

    suspend fun latest(pageOrder: Int, pageSize: Int): List<NewsDetailVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val newsDetailVOs = mutableListOf<NewsDetailVO>()
        newsDetailRepository.findByOrderByPublishTimeDesc(page).collect { newsDetailPO ->
            val latestNewsVO = newsDetailPO.asLatestNewsVO()
            newsDetailVOs.add(latestNewsVO)
        }
        return newsDetailVOs
    }

    suspend fun popular(startTime: Instant, pageOrder: Int, pageSize: Int): List<NewsTitleVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val newsTitleVOs = mutableListOf<NewsTitleVO>()
        newsDetailRepository.findByPublishTimeGreaterThanOrderByViewDesc(startTime, page)
            .collect { newsDetailPO ->
                val popularNewsVO = newsDetailPO.asPopularNewsVO()
                newsTitleVOs.add(popularNewsVO)
            }
        return newsTitleVOs
    }
}
