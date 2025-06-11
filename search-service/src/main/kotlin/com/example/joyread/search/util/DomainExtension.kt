package com.example.joyread.search.util

import com.example.joyread.common.domain.dto.NewsDetailDTO
import com.example.joyread.common.util.toEpochMilliStr
import com.example.joyread.search.domain.dto.KnowledgeRetrievalResponseDTO
import com.example.joyread.search.domain.vo.SearchResultVO

fun KnowledgeRetrievalResponseDTO.asSearchResultVO(newsDetailDTO: NewsDetailDTO) = with(newsDetailDTO) {
    SearchResultVO(docId, title, source, segments, publishTime.toEpochMilliStr(), coverImgFilename)
}
