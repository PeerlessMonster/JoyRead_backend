package com.example.joyread.common.domain.dto

import java.time.Instant

data class NewsDetailDTO(
    val id: String,
    val title: String,
    val publishTime: Instant,
    val view: UInt,
    val source: String,
    val writers: List<String>,
    val coverImgFilename: String
)
