package com.example.joyread.news.util

import com.example.joyread.common.util.toEpochMilliStr
import com.example.joyread.news.domain.po.NewsDetailPO
import com.example.joyread.news.domain.vo.LatestNewsVO

fun NewsDetailPO.asLatestNewsVO() = LatestNewsVO(id, title, publishUTC.toEpochMilliStr(), coverImgFilename)
