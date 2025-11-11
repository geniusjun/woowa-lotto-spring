package com.geniusjun.lotto.presentation.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    // 로또 도메인 검증 실패
    INVALID_LOTTO_NUMBER(HttpStatus.BAD_REQUEST, "로또 번호 형식이 올바르지 않습니다."),

    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
}
