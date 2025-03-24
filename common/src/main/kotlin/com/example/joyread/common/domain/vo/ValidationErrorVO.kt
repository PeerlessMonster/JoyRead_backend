package com.example.joyread.common.domain.vo

open class ErrorVO(
    val message: String
)

class ValidationErrorVO(
    val field: String,
    message: String
) : ErrorVO(message) {
    companion object {
        fun default() = ErrorVO("服务器不理解客户端的请求，未做任何处理。")
    }
}