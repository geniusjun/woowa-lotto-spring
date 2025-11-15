package com.geniusjun.lotto.presentation.common

import com.geniusjun.lotto.application.auth.exception.ExpiredJwtTokenException
import com.geniusjun.lotto.application.auth.exception.GoogleIdTokenInvalidException
import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import com.geniusjun.lotto.application.auth.exception.RefreshTokenMismatchException
import com.geniusjun.lotto.application.auth.exception.RefreshTokenNotFoundException
import com.geniusjun.lotto.domain.fortune.error.FortuneInvalidStateException
import com.geniusjun.lotto.domain.fortune.error.FortuneNotFoundException
import com.geniusjun.lotto.domain.lotto.exception.InvalidLottoException
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersNotFoundException
import com.geniusjun.lotto.domain.member.exception.DuplicateNicknameException
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

    @ExceptionHandler(DuplicateNicknameException::class)
    fun handleDuplicateNickname(ex: DuplicateNicknameException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.DUPLICATE_NICKNAME
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

    // ---- Auth 관련 예외 핸들러 ----

    @ExceptionHandler(GoogleIdTokenInvalidException::class)
    fun handleGoogleTokenInvalid(ex: GoogleIdTokenInvalidException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.AUTH_INVALID_GOOGLE_TOKEN
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(InvalidJwtTokenException::class)
    fun handleInvalidJwt(ex: InvalidJwtTokenException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.AUTH_INVALID_TOKEN
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(ExpiredJwtTokenException::class)
    fun handleExpiredJwt(ex: ExpiredJwtTokenException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.AUTH_EXPIRED_TOKEN
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(RefreshTokenNotFoundException::class)
    fun handleRefreshNotFound(ex: RefreshTokenNotFoundException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.AUTH_REFRESH_NOT_FOUND
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(RefreshTokenMismatchException::class)
    fun handleRefreshMismatch(ex: RefreshTokenMismatchException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.AUTH_REFRESH_MISMATCH
        return buildErrorResponse(code, ex)
    }

    // ---- Fortune 관련 예외 핸들러 ----

    @ExceptionHandler(FortuneNotFoundException::class)
    fun handleFortuneNotFound(ex: FortuneNotFoundException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.FORTUNE_NOT_FOUND
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(FortuneInvalidStateException::class)
    fun handleFortuneInvalidState(ex: FortuneInvalidStateException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.FORTUNE_INVALID_STATE
        return buildErrorResponse(code, ex)
    }

    // ---- 공통 예외 핸들러 (잡다한 IllegalArgument 등) ----

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.INVALID_INPUT
        return buildErrorResponse(code, ex)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.INTERNAL_SERVER_ERROR
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
