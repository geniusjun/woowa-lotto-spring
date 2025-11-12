package com.geniusjun.lotto.presentation.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    INVALID_LOTTO_NUMBER(HttpStatus.BAD_REQUEST, "로또 번호가 올바르지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MEMBER_BALANCE_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),
    WINNING_NOT_FOUND(HttpStatus.NOT_FOUND, "등록된 당첨 번호가 없습니다.")
}
