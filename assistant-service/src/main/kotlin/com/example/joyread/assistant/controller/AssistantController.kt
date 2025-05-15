package com.example.joyread.assistant.controller

import com.example.joyread.assistant.domain.dto.AssistantAnswerRequestDTO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerTextVO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerVO
import com.example.joyread.assistant.service.AssistantService
import com.example.joyread.common.constant.HTTPMediaTypeValue
import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping(
    "/assistant",
    consumes = [HTTPMediaTypeValue.APPLICATION_JSON_UTF8],
    produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
)
class AssistantController(
    private val assistantService: AssistantService
) {
    @PostMapping("/chat")
    suspend fun postChat(
        @RequestBody @Valid requestBody: AssistantAnswerRequestDTO
    ): ResponseEntity<Flow<ChunkAssistantAnswerVO>> {
        val assistantAnswerFlow = with(requestBody) {
            assistantService.chat(userId, question)
        }
        return ResponseEntity.ok(assistantAnswerFlow)
    }

    @PostMapping("/chat/context")
    suspend fun postChatWithContext(
        @RequestParam @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") newsId: String,
        @RequestBody @Valid requestBody: AssistantAnswerRequestDTO
    ): ResponseEntity<Flow<ChunkAssistantAnswerTextVO>> {
        val assistantAnswerFlow = with(requestBody) {
            assistantService.chat(userId, question, newsId).map { chunkAssistantAnswerVO ->
                chunkAssistantAnswerVO as ChunkAssistantAnswerTextVO
            }
        }
        return ResponseEntity.ok(assistantAnswerFlow)
    }
}
