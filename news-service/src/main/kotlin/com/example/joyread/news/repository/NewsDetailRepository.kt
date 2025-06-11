package com.example.joyread.news.repository

import com.example.joyread.news.domain.po.NewsDetailPO
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

interface NewsDetailRepository : CoroutineCrudRepository<NewsDetailPO, String> {
    fun findByOrderByPublishTimeDesc(pageable: Pageable): Flow<NewsDetailPO>

    fun findByPublishTimeGreaterThanOrderByViewDesc(publishTime: Instant, pageable: Pageable): Flow<NewsDetailPO>
}
