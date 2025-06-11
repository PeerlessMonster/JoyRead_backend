package com.example.joyread.search.controller

import com.example.joyread.common.constant.HTTPMediaTypeValue
import com.example.joyread.search.domain.vo.SearchResultVO
import com.example.joyread.search.service.KnowledgeService
import com.example.joyread.search.service.NewsDetailService
import com.example.joyread.search.util.asSearchResultVO
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search", produces = [HTTPMediaTypeValue.APPLICATION_JSON_UTF8])
class KnowledgeController(
    private val knowledgeService: KnowledgeService,
    private val newsDetailService: NewsDetailService
) {
    @GetMapping
    suspend fun getSearchResult(
        @RequestParam @NotBlank(message = "不能为空") query: String
    ): ResponseEntity<List<SearchResultVO>> {
        val knowledgeRetrievalResponseDTOs = knowledgeService.search(query)
        val searchResultVOs = knowledgeRetrievalResponseDTOs.map { knowledgeRetrievalResponseDTO ->
            val newsId = knowledgeRetrievalResponseDTO.docId
            val newsDetailDTO = newsDetailService.getNewsDetail(newsId)
            knowledgeRetrievalResponseDTO.asSearchResultVO(newsDetailDTO)
        }
        return ResponseEntity.ok(searchResultVOs)
    }
}
