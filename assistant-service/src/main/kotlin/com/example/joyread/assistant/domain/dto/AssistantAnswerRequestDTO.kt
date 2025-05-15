package com.example.joyread.assistant.domain.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AssistantAnswerRequestDTO(
    @field:Length(min = 24, max = 24, message = "须符合 ObjectId 格式") val userId: String,
    @field:NotBlank(message = "不能为空") val question: String
)
