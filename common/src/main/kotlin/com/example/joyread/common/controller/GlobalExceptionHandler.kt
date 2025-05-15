package com.example.joyread.common.controller

import com.example.joyread.common.domain.vo.ErrorVO
import com.example.joyread.common.domain.vo.ValidationErrorVO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.server.MissingRequestValueException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MissingRequestValueException::class)
    suspend fun handleMissingRequestParamException(e: MissingRequestValueException): ResponseEntity<ErrorVO> {
        val name = e.name

//        val label = e.label
//        val type = e.type

        return ResponseEntity.badRequest().body(ValidationErrorVO(name, "请求参数不能为空"))
    }

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
            } else if (parameterName != null) {
                ValidationErrorVO(parameterName, "格式错误")
            } else {
                ErrorVO()
            }
        )
    }

    @ExceptionHandler(WebExchangeBindException::class)
    suspend fun handleRequestBodyFieldInvalidException(e: WebExchangeBindException): ResponseEntity<ErrorVO> {
        val fieldError = e.fieldError

        val fieldName = fieldError?.field
        val message = fieldError?.defaultMessage

        return ResponseEntity.badRequest().body(
            if (fieldName != null && message != null) {
                ValidationErrorVO(fieldName, message)
            } else if (fieldName != null) {
                ValidationErrorVO(fieldName, "格式错误")
            } else {
                ErrorVO()
            }
        )
    }
}
