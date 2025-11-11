package com.geniusjun.lotto.domain.lotto

class LottoNumbers private constructor(
    val numbers: List<LottoNumber>
) {

    init {
        validateSize(numbers)
        validateDistinct(numbers)
    }

    private fun validateSize(numbers: List<LottoNumber>) {
        if (numbers.size != LOTTO_SIZE) {
            throw InvalidLottoException(
                "로또 번호는 정확히 ${LOTTO_SIZE}개여야 합니다. (입력된 개수: ${numbers.size})"
            )
        }
    }

    private fun validateDistinct(numbers: List<LottoNumber>) {
        if (numbers.distinctBy { it.value }.size != LOTTO_SIZE) {
            throw InvalidLottoException(
                "로또 번호는 중복될 수 없습니다. (입력된 번호: ${numbers.map { it.value }})"
            )
        }
    }

    companion object {
        private const val LOTTO_SIZE = 6

        fun of(rawNumbers: List<Int>): LottoNumbers {
            val lottoNumbers = rawNumbers.map { LottoNumber.of(it) }
            return LottoNumbers(lottoNumbers)
        }
    }
}
