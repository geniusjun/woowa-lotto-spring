package com.geniusjun.lotto.presentation.common

import com.geniusjun.lotto.domain.lotto.InvalidLottoException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLottoException::class)
    fun handleInvalidLotto(ex: InvalidLottoException): ResponseEntity<ErrorResponse> {
        val code = ErrorCode.INVALID_LOTTO_NUMBER
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
