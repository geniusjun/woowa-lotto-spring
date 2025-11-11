package com.geniusjun.lotto.presentation.common

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 코틀린 require(...) 가 던지는 예외
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.BAD_REQUEST
        val body = ErrorResponse(
            code = errorCode.name,
            message = ex.message ?: errorCode.message
        )
        return ResponseEntity.status(errorCode.status).body(body)
    }

}
