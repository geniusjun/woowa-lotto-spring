package com.geniusjun.lotto.domain.lotto

data class LottoResult(
    val matchCount: Int,
    val bonusMatched: Boolean,
    val rank: LottoRank,
    val reward: Long
)
