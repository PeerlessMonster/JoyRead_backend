package com.example.joyread.news.domain.po

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("news")
data class NewsPO(
    @Id val id: String,
    val title: String,
    val publishTime: Instant,
    val view: UInt,
    val source: String,
    val writers: List<String>,
    @Field("coverImg") val coverImgFilename: String,
    val content: List<Block.ParagraphBlock>
)

enum class BlockType {
    HEADING, IMAGE, ANNOTATION, CONTEXT, QUOTE, BODY, LEADING, SPAN
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

    data class AnnotationBlock(
        val spans: List<SpanBlock>
    ) : ParagraphBlock(BlockType.ANNOTATION)

    data class ContextBlock(
        val paragraphs: List<ParagraphBlock>
    ) : ParagraphBlock(BlockType.CONTEXT)

    data class QuoteBlock(
        val paragraphs: List<ParagraphBlock>
    ) : ParagraphBlock(BlockType.QUOTE)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class BodyBlock(
        val spans: List<SpanBlock>,
        val leading: LeadingBlock? = null
    ) : ParagraphBlock(BlockType.BODY)

    data class LeadingBlock(
        val text: String,
        val style: LeadingStyle
    ) : Block(BlockType.LEADING)

    data class SpanBlock(
        val text: String,
        val style: SpanStyle = SpanStyle.NORMAL
    ) : Block(BlockType.SPAN)
}

enum class LeadingStyle {
    SIGN, ORDER
}

enum class SpanStyle {
    NORMAL, BOLD, ITALIC, COLORED, BOLD_COLORED, ITALIC_BOLD
}
