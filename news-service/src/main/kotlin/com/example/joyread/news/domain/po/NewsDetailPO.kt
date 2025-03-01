package com.example.joyread.news.domain.po

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("newsDetail")
data class NewsDetailPO(
    @Id val id: String,
    val title: String,
    @Field("publishTime") val publishUTC: Instant,
    val source: String,
    val writers: List<String>,
    @Field("coverImg") val coverImgFilename: String
)
