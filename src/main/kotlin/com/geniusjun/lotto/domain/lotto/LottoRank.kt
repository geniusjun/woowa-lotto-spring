package com.geniusjun.lotto.domain.lotto

enum class LottoRank(
    val matchCount: Int,
    val needBonus: Boolean,
    val reward: Long
) {
    FIRST(6, false, 2_000_000_000L),
    SECOND(5, true, 50_000_000L),
    THIRD(5, false, 1_500_000L),
    FOURTH(4, false, 50_000L),
    FIFTH(3, false, 5_000L),
    NONE(0, false, 0L);

    companion object {
        fun of(matchCount: Int, bonusMatched: Boolean): LottoRank {
            return when {
                matchCount == 6 -> FIRST
                matchCount == 5 && bonusMatched -> SECOND
                matchCount == 5 -> THIRD
                matchCount == 4 -> FOURTH
                matchCount == 3 -> FIFTH
                else -> NONE
            }
        }
    }
}
