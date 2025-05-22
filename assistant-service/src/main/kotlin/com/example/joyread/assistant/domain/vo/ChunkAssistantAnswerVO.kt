package com.example.joyread.assistant.domain.vo

import com.example.joyread.common.domain.vo.NewsTitleVO

enum class ChunkAssistantAnswerEvent {
    SOURCE, ANSWER
}

sealed class ChunkAssistantAnswerVO(
    val event: ChunkAssistantAnswerEvent
)

data class ChunkAssistantAnswerTextVO(
    val markdown: String
) : ChunkAssistantAnswerVO(ChunkAssistantAnswerEvent.ANSWER)

data class AssistantAnswerSourceVO(
    val sources: List<NewsTitleVO>
) : ChunkAssistantAnswerVO(ChunkAssistantAnswerEvent.SOURCE)
