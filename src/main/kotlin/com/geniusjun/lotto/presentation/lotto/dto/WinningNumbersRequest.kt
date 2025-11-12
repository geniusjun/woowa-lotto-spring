package com.geniusjun.lotto.presentation.lotto.dto

data class WinningNumbersRequest(
    val round: Long,
    val mainNumbers: List<Int>,
    val bonusNumber: Int?
)
