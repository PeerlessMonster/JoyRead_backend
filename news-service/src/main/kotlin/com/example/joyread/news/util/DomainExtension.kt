package com.example.joyread.news.util

import com.example.joyread.common.util.toEpochMilliStr
import com.example.joyread.news.domain.po.Block.ParagraphBlock
import com.example.joyread.news.domain.po.NewsDetailPO
import com.example.joyread.news.domain.po.NewsPO
import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.domain.vo.NewsVO

fun NewsDetailPO.asLatestNewsVO() = LatestNewsVO(id, title, publishUTC.toEpochMilliStr(), coverImgFilename)

fun NewsPO.asNewsVO(amendContent: List<ParagraphBlock>? = null) =
    NewsVO(title, publishUTC.toEpochMilliStr(), source, writers, amendContent ?: content)
