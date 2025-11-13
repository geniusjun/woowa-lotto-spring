package com.geniusjun.lotto.presentation.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    INVALID_LOTTO_NUMBER(HttpStatus.BAD_REQUEST, "로또 번호가 올바르지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MEMBER_BALANCE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
    WINNING_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 당첨 번호가 없습니다."),

    // Auth 관련
    AUTH_INVALID_GOOGLE_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 구글 로그인 토큰입니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 토큰입니다."),
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다."),
    AUTH_REFRESH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),
    AUTH_REFRESH_MISMATCH(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다."),

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.")
}
