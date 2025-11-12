package com.geniusjun.lotto.domain.lotto

import com.geniusjun.lotto.domain.lotto.exception.InvalidLottoException

class DrawResult private constructor(
    val numbers: LottoNumbers,
    val bonusNumber: LottoNumber?
) {

    init {
        validateBonus()
    }

    private fun validateBonus() {
        bonusNumber?.let { bonus ->
            if (numbers.values.any { it.value == bonus.value }) {
                throw InvalidLottoException(
                    "보너스 번호는 당첨 번호와 중복될 수 없습니다. (보너스: ${bonus.value})"
                )
            }
        }
    }

    companion object {
        fun of(mainNumbers: List<Int>, bonus: Int? = null): DrawResult {
            return DrawResult(
                LottoNumbers.of(mainNumbers),
                bonus?.let { LottoNumber.of(it) }
            )
        }
    }
}
