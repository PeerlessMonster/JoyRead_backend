package com.example.joyread.news.util

import com.example.joyread.common.util.toEpochMilliStr
import com.example.joyread.news.domain.po.Block.ParagraphBlock
import com.example.joyread.news.domain.po.NewsDetailPO
import com.example.joyread.news.domain.po.NewsPO
import com.example.joyread.news.domain.vo.LatestNewsVO
import com.example.joyread.news.domain.vo.NewsVO
import com.example.joyread.news.domain.vo.PopularNewsVO

fun NewsDetailPO.asLatestNewsVO() = LatestNewsVO(id, title, publishTime.toEpochMilliStr(), coverImgFilename)

fun NewsDetailPO.asPopularNewsVO() = PopularNewsVO(id, title)

fun NewsPO.asNewsVO(amendContent: List<ParagraphBlock>? = null) =
    NewsVO(title, publishTime.toEpochMilliStr(), source, writers, amendContent ?: content)
