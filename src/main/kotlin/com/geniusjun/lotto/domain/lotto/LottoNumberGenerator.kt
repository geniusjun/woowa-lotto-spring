package com.geniusjun.lotto.domain.lotto

object LottoNumberGenerator {

    private const val MIN_NUMBER = 1
    private const val MAX_NUMBER = 45
    private const val LOTTO_SIZE = 6

    fun generate(): LottoNumbers {
        val picked = (MIN_NUMBER..MAX_NUMBER)
            .shuffled() // 섞고
            .take(LOTTO_SIZE) // 뽑은다음
            .sorted() // 정렬

        return LottoNumbers.of(picked)
    }
}
