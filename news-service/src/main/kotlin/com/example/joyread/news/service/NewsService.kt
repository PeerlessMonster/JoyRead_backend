package com.example.joyread.news.service

import com.example.joyread.news.domain.po.Block.ImageBlock
import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.repository.NewsDetailRepository
import com.example.joyread.news.repository.NewsRepository
import com.example.joyread.news.util.asLatestNewsVO
import com.example.joyread.news.util.asNewsVO
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsDetailRepository: NewsDetailRepository,
    private val newsRepository: NewsRepository
) {
    suspend fun latest(pageOrder: Int, pageSize: Int): List<LatestNewsVO> {
        val page = PageRequest.of(pageOrder, pageSize)

        val latestNewsVOs = mutableListOf<LatestNewsVO>()
        newsDetailRepository.findByOrderByPublishUTCDesc(page).collect { newsDetailPO ->
            val newsVO = newsDetailPO.asLatestNewsVO()
            latestNewsVOs.add(newsVO)
        }
        return latestNewsVOs
    }

    suspend fun article(id: String) = newsRepository.findById(id)?.let { newsPO ->
        val coverImageBlock = ImageBlock(newsPO.coverImgFilename)

        val amendContent = newsPO.content.toMutableList()
        amendContent.addFirst(coverImageBlock)

        newsPO.asNewsVO(amendContent)
    }
}
