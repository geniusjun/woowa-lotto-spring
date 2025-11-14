package com.geniusjun.lotto.presentation.common

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(data = data)
    }
}
