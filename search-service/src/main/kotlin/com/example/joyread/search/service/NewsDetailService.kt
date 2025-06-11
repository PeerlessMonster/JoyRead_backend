package com.example.joyread.search.service

import com.example.joyread.common.constant.HTTPMediaTypeValue
import com.example.joyread.common.domain.dto.NewsDetailDTO
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange("/news", accept = [HTTPMediaTypeValue.APPLICATION_JSON_UTF8])
interface NewsDetailService {
    @GetExchange("/{id}/detail")
    suspend fun getNewsDetail(@PathVariable id: String): NewsDetailDTO
}
