package com.example.joyread.search.domain.vo

data class SearchResultVO(
    val id: String,
    val title: String,
    val source: String,
    val hitSegments: List<String>,
    val publishUTCEpochMilli: String,
    val coverImgFilename: String
)
