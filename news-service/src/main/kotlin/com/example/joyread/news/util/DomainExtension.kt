package com.example.joyread.news.util

import com.example.joyread.common.util.toEpochMilliStr
import com.example.joyread.news.domain.po.Block.ParagraphBlock
import com.example.joyread.news.domain.po.NewsDetailPO
import com.example.joyread.news.domain.po.NewsPO
import com.example.joyread.news.domain.vo.NewsDetailVO
import com.example.joyread.news.domain.vo.NewsVO
import com.example.joyread.common.domain.vo.NewsTitleVO

fun NewsDetailPO.asLatestNewsVO() = NewsDetailVO(id, title, publishTime.toEpochMilliStr(), coverImgFilename)

fun NewsDetailPO.asPopularNewsVO() = NewsTitleVO(id, title)

fun NewsPO.asNewsVO(amendContent: List<ParagraphBlock>? = null) =
    NewsVO(title, publishTime.toEpochMilliStr(), view, source, writers, amendContent ?: content)
