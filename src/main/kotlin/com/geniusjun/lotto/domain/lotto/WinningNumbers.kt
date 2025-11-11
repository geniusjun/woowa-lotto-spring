package com.geniusjun.lotto.domain.lotto

class WinningNumbers private constructor(
    val numbers: LottoNumbers,
    val bonusNumber: LottoNumber?
) {

    init {
        validateBonus()
    }

    private fun validateBonus() {
        bonusNumber?.let { bonus ->
            if (numbers.numbers.any { it.value == bonus.value }) {
                throw InvalidLottoException(
                    "보너스 번호는 당첨 번호와 중복될 수 없습니다. (보너스: ${bonus.value})"
                )
            }
        }
    }

    companion object {
        fun of(mainNumbers: List<Int>, bonus: Int? = null): WinningNumbers {
            return WinningNumbers(
                LottoNumbers.of(mainNumbers),
                bonus?.let { LottoNumber.of(it) }
            )
        }
    }
}
