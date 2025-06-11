package com.example.joyread.news.service

import com.example.joyread.news.domain.po.Block.ImageBlock
import com.example.joyread.news.repository.NewsRepository
import com.example.joyread.news.util.asNewsVO
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {
    suspend fun read(id: String) = newsRepository.findById(id)?.let { newsPO ->
        val coverImageBlock = ImageBlock(newsPO.coverImgFilename)

        val amendContent = newsPO.content.toMutableList()
        amendContent.addFirst(coverImageBlock)

        newsPO.asNewsVO(amendContent)
    }
}
