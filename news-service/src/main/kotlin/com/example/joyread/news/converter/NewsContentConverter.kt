package com.example.joyread.news.converter

import com.example.joyread.news.domain.po.Block.AnnotationBlock
import com.example.joyread.news.domain.po.Block.BodyBlock
import com.example.joyread.news.domain.po.Block.ContextBlock
import com.example.joyread.news.domain.po.Block.HeadingBlock
import com.example.joyread.news.domain.po.Block.ImageBlock
import com.example.joyread.news.domain.po.Block.LeadingBlock
import com.example.joyread.news.domain.po.Block.ParagraphBlock
import com.example.joyread.news.domain.po.Block.QuoteBlock
import com.example.joyread.news.domain.po.Block.SpanBlock
import com.example.joyread.news.domain.po.LeadingStyle
import com.example.joyread.news.domain.po.SpanStyle
import com.example.joyread.news.exception.DocumentConversionException
import org.bson.Document
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class NewsContentConverter : Converter<Document, ParagraphBlock> {
    override fun convert(source: Document) = paragraphConvert(source)

    private fun paragraphConvert(source: Document): ParagraphBlock {
        val type = source.get("type") as String
        return when (type) {
            "heading" -> {
                val text = source.get("text") as String
                val levelSource = source.get("level") as Int

                val level = levelSource.toUByte()
                HeadingBlock(text, level)
            }

            "img" -> {
                val filename = source.get("filename") as String
                ImageBlock(filename)
            }

            "annotation" -> {
                val spansSource = source.get("spans") as List<*>
                val spans = spanConvertList(spansSource)
                AnnotationBlock(spans)
            }

            "context" -> {
                val paragraphsSource = source.get("paragraphs") as List<*>
                val paragraphs = paragraphConvertList(paragraphsSource)
                ContextBlock(paragraphs)
            }

            "quote" -> {
                val paragraphsSource = source.get("paragraphs") as List<*>
                val paragraphs = paragraphConvertList(paragraphsSource)
                QuoteBlock(paragraphs)
            }

            "body" -> {
                val leadingSource = source.get("leading")
                val spansSource = source.get("spans") as List<*>

                val spans = spanConvertList(spansSource)
                if (leadingSource != null) {
                    val leading = leadingConvert(leadingSource as Document)
                    BodyBlock(spans, leading)
                } else {
                    BodyBlock(spans)
                }
            }

            else -> throw DocumentConversionException("No enum named $type in RenderType")
        }
    }

    private fun paragraphConvertList(sources: List<*>) = sources.map { source ->
        paragraphConvert(source as Document)
    }

    private fun leadingConvert(source: Document): LeadingBlock {
        val text = source.get("text") as String
        val styleSource = source.get("style") as String

        val style = when (styleSource) {
            "sign" -> LeadingStyle.SIGN
            "order" -> LeadingStyle.ORDER
            else -> throw DocumentConversionException("No enum named $styleSource in LeadingStyle")
        }
        return LeadingBlock(text, style)
    }

    private fun spanConvert(source: Document): SpanBlock {
        val text = source.get("text") as String
        val styleSource = source.get("style")
        if (styleSource == null) {
            return SpanBlock(text)
        }

        val style = when (styleSource as String) {
            "bold" -> SpanStyle.BOLD
            "italic" -> SpanStyle.ITALIC
            "colored" -> SpanStyle.COLORED
            "bold,colored" -> SpanStyle.BOLD_COLORED
            "italic,bold" -> SpanStyle.ITALIC_BOLD
            "normal" -> SpanStyle.NORMAL
            else -> throw DocumentConversionException("No enum named $styleSource in TextStyle")
        }
        return SpanBlock(text, style)
    }

    private fun spanConvertList(sources: List<*>) = sources.map { source ->
        spanConvert(source as Document)
    }
}
