package com.example.joyread.news.domain.po

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("news")
data class NewsPO(
    @Id val id: String,
    val title: String,
    val publishTime: Instant,
    val source: String,
    val writers: List<String>,
    @Field("coverImg") val coverImgFilename: String,
    val content: List<Block.ParagraphBlock>
)

enum class BlockType {
    HEADING, IMAGE, IMAGE_DESCRIPTION, CONTEXT, QUOTE, BODY, SPAN
}

abstract class Block(val type: BlockType) {
    sealed class ParagraphBlock(type: BlockType) : Block(type)

    data class HeadingBlock(
        val text: String,
        val level: UByte
    ) : ParagraphBlock(BlockType.HEADING)

    data class ImageBlock(
        val filename: String,
    ) : ParagraphBlock(BlockType.IMAGE)

    data class ImageDescriptionBlock(
        val spans: List<SpanBlock>
    ) : ParagraphBlock(BlockType.IMAGE_DESCRIPTION)

    data class ContextBlock(
        val paragraphs: List<ParagraphBlock>
    ) : ParagraphBlock(BlockType.CONTEXT)

    data class QuoteBlock(
        val paragraphs: List<ParagraphBlock>
    ) : ParagraphBlock(BlockType.QUOTE)

    data class BodyBlock(
        val spans: List<SpanBlock>
    ) : ParagraphBlock(BlockType.BODY)

    data class SpanBlock(
        val text: String,
        val style: SpanStyle = SpanStyle.NORMAL
    ) : Block(BlockType.SPAN)
}

enum class SpanStyle {
    SIGN, ORDER, NORMAL, BOLD, ITALIC, COLORED
}