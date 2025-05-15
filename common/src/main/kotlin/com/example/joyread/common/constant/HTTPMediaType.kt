package com.example.joyread.common.constant

import org.springframework.http.MediaType
import java.nio.charset.Charset

object HTTPMediaType {
    val APPLICATION_JSON_UTF8 = MediaType("application", "json", Charset.defaultCharset())
}

object HTTPMediaTypeValue {
    const val APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8"
}
