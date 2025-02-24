package com.example.joyread.news.domain.po

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("chronologicalNewsBanner")
data class LatestNewsPO(
    @Id val id: String,
    val title: String,
    @Field("coverImg") val coverImgFilename: String
)
