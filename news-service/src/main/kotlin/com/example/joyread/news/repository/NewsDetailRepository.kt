package com.example.joyread.news.repository

import com.example.joyread.news.domain.po.NewsDetailPO
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface NewsDetailRepository : Repository<NewsDetailPO, String> {
    fun findByOrderByPublishUTCDesc(pageable: Pageable): Flow<NewsDetailPO>
}
