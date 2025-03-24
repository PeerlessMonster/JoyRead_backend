package com.example.joyread.news.domain.vo

import com.example.joyread.news.domain.po.Block.ParagraphBlock

data class NewsVO(
    val title: String,
    val publishUTCEpochMilli: String,
    val source: String,
    val writers: List<String>,
    val content: List<ParagraphBlock>
)
