package com.example.joyread.search.service

import com.example.joyread.search.domain.dto.KnowledgeRetrievalRequestDTO
import com.example.joyread.search.domain.dto.KnowledgeRetrievalResponseDTO
import com.example.joyread.search.handler.DifyHandler
import org.springframework.stereotype.Service

@Service
class KnowledgeService(
    private val difyHandler: DifyHandler
) {
    suspend fun search(query: String): List<KnowledgeRetrievalResponseDTO> {
        val knowledgeRetrievalRequestDTO = KnowledgeRetrievalRequestDTO.hybridSearch(query)
        val json = difyHandler.retrieveKnowledge(knowledgeRetrievalRequestDTO)

        val records = json.get("records").asIterable()
        return records.map { record ->
            val segment = record.get("segment")
            val document = segment.get("document")
            val docMetadata = document.get("doc_metadata")
            val id = docMetadata.get("id").asText()

            val childChunks = record.get("child_chunks").asIterable()
            val segments = childChunks.map { childChunk ->
                childChunk.get("content").asText()
            }

            KnowledgeRetrievalResponseDTO(id, segments)
        }
    }
}
