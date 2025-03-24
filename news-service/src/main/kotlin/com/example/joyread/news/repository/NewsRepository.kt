package com.example.joyread.news.repository

import com.example.joyread.news.domain.po.NewsPO
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface NewsRepository : CoroutineCrudRepository<NewsPO, String>
