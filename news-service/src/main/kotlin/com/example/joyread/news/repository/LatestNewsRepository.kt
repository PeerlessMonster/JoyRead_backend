package com.example.joyread.news.repository

import com.example.joyread.news.domain.po.LatestNewsPO
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface LatestNewsRepository : ReactiveMongoRepository<LatestNewsPO, String> {
    fun findAllBy(pageable: Pageable): Flow<LatestNewsPO>
}
