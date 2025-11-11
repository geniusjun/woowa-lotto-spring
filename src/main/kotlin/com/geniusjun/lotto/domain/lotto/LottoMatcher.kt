package com.geniusjun.lotto.domain.lotto

object LottoMatcher {

    fun match(myNumbers: LottoNumbers, winning: WinningNumbers): LottoResult {
        val winningValues = winning.numbers.toIntList()
        val myValues = myNumbers.toIntList()

        // 몇 개가 일치하는지
        val matchCount = myValues.count { it in winningValues }

        // 보너스 일치 여부
        val bonusMatched = winning.bonusNumber?.let { bonus ->
            myValues.contains(bonus.value)
        } ?: false

        val rank = LottoRank.of(matchCount, bonusMatched)

        return LottoResult(
            matchCount = matchCount,
            bonusMatched = bonusMatched,
            rank = rank,
            reward = rank.reward
        )
    }
}

