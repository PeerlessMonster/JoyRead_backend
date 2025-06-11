package com.example.joyread.search.domain.dto

data class KnowledgeRetrievalResponseDTO(
    val docId: String,
    val segments: List<String>
)
