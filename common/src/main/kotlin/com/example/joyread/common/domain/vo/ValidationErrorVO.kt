package com.example.joyread.common.domain.vo

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ValidationErrorVO(
    message: String,
    val field: String? = null,
) : ErrorVO(message)
