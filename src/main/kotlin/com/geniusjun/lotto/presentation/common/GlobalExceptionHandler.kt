package com.geniusjun.lotto.presentation.common

import com.geniusjun.lotto.domain.lotto.exception.InvalidLottoException
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersNotFoundException
import com.geniusjun.lotto.domain.member.exception.InvalidBalanceException
import com.geniusjun.lotto.domain.member.exception.MemberNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLottoException::class)
    fun handleInvalidLotto(ex: InvalidLottoException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.INVALID_LOTTO_NUMBER
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(MemberNotFoundException::class)
    fun handleMemberNotFound(ex: MemberNotFoundException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.MEMBER_NOT_FOUND
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(InvalidBalanceException::class)
    fun handleInvalidBalance(ex: InvalidBalanceException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.MEMBER_BALANCE_NOT_ENOUGH
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(WinningNumbersNotFoundException::class)
    fun handleWinningNumbersNotFound(ex: WinningNumbersNotFoundException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.WINNING_NOT_FOUND
        return buildErrorResponse(code, ex)
    }

    private fun buildErrorResponse(code: ErrorCode, ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(code.status)
            .body(
                ErrorResponse(
                    code = code.name,
                    message = ex.message ?: code.message
                )
            )
    }
}
