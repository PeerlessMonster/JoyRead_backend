package com.example.joyread.common.domain.vo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ValidationErrorVO(
    val field: String,
    message: String
) : ErrorVO(message)
