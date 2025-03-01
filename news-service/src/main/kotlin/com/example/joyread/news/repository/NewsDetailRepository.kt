package com.example.joyread.news.repository

import com.example.joyread.news.domain.po.NewsDetailPO
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface NewsDetailRepository : ReactiveMongoRepository<NewsDetailPO, String> {
    fun findByOrderByPublishUTCDesc(pageable: Pageable): Flow<NewsDetailPO>
}
