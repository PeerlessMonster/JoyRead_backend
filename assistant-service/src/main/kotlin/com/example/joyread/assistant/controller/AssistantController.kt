package com.example.joyread.assistant.controller

import com.example.joyread.assistant.domain.dto.AssistantAnswerRequestDTO
import com.example.joyread.assistant.domain.vo.ChunkAssistantAnswerVO
import com.example.joyread.assistant.service.AssistantService
import com.example.joyread.common.constant.HTTPMediaTypeValue
import kotlinx.coroutines.flow.Flow
import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    "/assistant",
    consumes = [HTTPMediaTypeValue.APPLICATION_JSON_UTF8],
    produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
)
class AssistantController(
    private val assistantService: AssistantService
) {
    @PostMapping("/chat")
    suspend fun postQuestion(
        @RequestParam @Length(min = 24, max = 24, message = "须符合 ObjectId 格式") newsId: String?,
        @RequestBody @Validated requestBody: AssistantAnswerRequestDTO
    ): ResponseEntity<Flow<ChunkAssistantAnswerVO>> {
        val assistantAnswerFlow = with(requestBody) {
            if (newsId == null) {
                assistantService.chat(userId, question)
            } else {
                assistantService.chatUnderContext(userId, question, newsId)
            }
        }
        return ResponseEntity.ok(assistantAnswerFlow)
    }
}
