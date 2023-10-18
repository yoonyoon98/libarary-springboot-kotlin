package com.example.libarary.global.error

import com.example.libarary.global.response.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonControllerAdvice {

    private val log = LoggerFactory.getLogger(this::class.java)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun onException(e: Exception): CommonResponse<*> {
        log.error("error ", e)
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR)
    }
}