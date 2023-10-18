package com.example.libarary.global.response

import com.example.libarary.global.error.ErrorCode

class CommonResponse<T>(
    val result: Result,
    val data: T,
    val message: String?,
    val errorCode: String?
) {

    enum class Result {
        SUCCESS, FAIL
    }

    companion object {
        fun <T> success(data: T, message: String?): CommonResponse<T> = CommonResponse(Result.SUCCESS, data, message, null)
        fun fail(errorCode: ErrorCode): CommonResponse<*> = CommonResponse(Result.FAIL, null, errorCode.message, errorCode.name)
    }
}