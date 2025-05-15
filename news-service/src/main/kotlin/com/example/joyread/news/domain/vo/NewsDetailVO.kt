package com.example.joyread.news.domain.vo

data class NewsDetailVO(
    val id: String,
    val title: String,
    val publishUTCEpochMilli: String,
    val coverImgFilename: String,
)
