package com.example.libarary.global.error

enum class ErrorCode(val message: String) {
    NO_EXIST_USER("회원 정보를 찾을 수 없습니다."),
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
}