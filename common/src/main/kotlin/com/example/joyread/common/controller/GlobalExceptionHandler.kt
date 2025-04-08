package com.example.joyread.common.controller

import com.example.joyread.common.domain.vo.ErrorVO
import com.example.joyread.common.domain.vo.ValidationErrorVO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.method.annotation.HandlerMethodValidationException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException::class)
    fun handleRequestParamNullException(e: IllegalStateException) =
        ResponseEntity.badRequest().body(ErrorVO("请求参数不能为空"))

    @ExceptionHandler(HandlerMethodValidationException::class)
    suspend fun handleRequestParamInvalidException(e: HandlerMethodValidationException): ResponseEntity<ErrorVO> {
        val parameterValidationResult = e.parameterValidationResults.first()

        val methodParameter = parameterValidationResult.methodParameter
        val parameterName = methodParameter.parameterName

//        val argument = parameterValidationResult.argument

        val resolvableError = parameterValidationResult.resolvableErrors.first()
        val message = resolvableError.defaultMessage

        return ResponseEntity.badRequest().body(
            if (parameterName != null && message != null) {
                ValidationErrorVO(parameterName, message)
            } else {
                ValidationErrorVO.default()
            }
        )
    }

    @ExceptionHandler(WebExchangeBindException::class)
    suspend fun handleWebExchangeBindException(e: WebExchangeBindException): ResponseEntity<ErrorVO> {
        val fieldError = e.fieldError

        val fieldName = fieldError?.field
        val message = fieldError?.defaultMessage

        return ResponseEntity.badRequest().body(
            if (fieldName != null && message != null) {
                ValidationErrorVO(fieldName, message)
            } else {
                ValidationErrorVO.default()
            }
        )
    }
}