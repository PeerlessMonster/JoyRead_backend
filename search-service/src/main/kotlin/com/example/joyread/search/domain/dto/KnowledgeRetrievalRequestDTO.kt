package com.example.joyread.search.domain.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KnowledgeRetrievalRequestDTO(
    val query: String,
    val retrievalModel: RetrievalModel
) {
    companion object {
        fun hybridSearch(query: String) =
            KnowledgeRetrievalRequestDTO(query, RetrievalModel("hybrid_search", false, 0.7F, 10U, false))
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RetrievalModel(
    val searchMethod: String,
    val rerankingEnable: Boolean,
    val weights: Float,
    val topK: UShort,
    val scoreThresholdEnabled: Boolean
)
