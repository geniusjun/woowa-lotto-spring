package com.geniusjun.lotto.domain.lotto

class LottoNumber private constructor(
    val value: Int
) {

    init {
        validateRange(value)
    }

    private fun validateRange(value: Int) {
        if (value !in MIN_NUMBER..MAX_NUMBER) {
            throw InvalidLottoException(
                "로또 번호는 ${MIN_NUMBER}부터 ${MAX_NUMBER} 사이여야 합니다. (입력: $value)"
            )
        }
    }

    companion object {
        private const val MIN_NUMBER = 1
        private const val MAX_NUMBER = 45

        fun of(value: Int): LottoNumber {
            return LottoNumber(value)
        }
    }
}
