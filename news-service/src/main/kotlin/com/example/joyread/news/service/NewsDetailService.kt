package com.example.joyread.news.service

import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.domain.vo.PopularNewsVO
import com.example.joyread.news.repository.NewsDetailRepository
import com.example.joyread.news.util.asLatestNewsVO
import com.example.joyread.news.util.asPopularNewsVO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NewsDetailService(
    private val newsDetailRepository: NewsDetailRepository
) {
    suspend fun latest(pageOrder: Int, pageSize: Int): List<LatestNewsVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val latestNewsVOs = mutableListOf<LatestNewsVO>()
        newsDetailRepository.findByOrderByPublishTimeDesc(page).collect { newsDetailPO ->
            val latestNewsVO = newsDetailPO.asLatestNewsVO()
            latestNewsVOs.add(latestNewsVO)
        }
        return latestNewsVOs
    }

    suspend fun popular(startTime: Instant, pageOrder: Int, pageSize: Int): List<PopularNewsVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val popularNewsVOs = mutableListOf<PopularNewsVO>()
        newsDetailRepository.findByPublishTimeGreaterThanOrderByViewDesc(startTime, page)
            .collect { newsDetailPO ->
                val popularNewsVO = newsDetailPO.asPopularNewsVO()
                popularNewsVOs.add(popularNewsVO)
            }
        return popularNewsVOs
    }
}
