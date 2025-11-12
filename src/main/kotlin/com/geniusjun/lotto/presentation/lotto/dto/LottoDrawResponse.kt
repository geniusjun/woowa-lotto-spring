package com.geniusjun.lotto.presentation.lotto.dto

data class LottoDrawResponse(
    val myNumbers: List<Int>,
    val winningNumbers: List<Int>,
    val matchedNumbers: List<Int>,
    val bonusNumber: Int?,
    val bonusMatched: Boolean,
    val rank: String,
    val reward: Long,
    val balance: Long
) {
    companion object {
        fun from(
            myNumbers: List<Int>,
            winningNumbers: List<Int>,
            matchedNumbers: List<Int>,
            bonusNumber: Int?,
            bonusMatched: Boolean,
            rank: String,
            reward: Long,
            balance: Long
        ) = LottoDrawResponse(
            myNumbers,
            winningNumbers,
            matchedNumbers,
            bonusNumber,
            bonusMatched,
            rank,
            reward,
            balance
        )
    }
}
