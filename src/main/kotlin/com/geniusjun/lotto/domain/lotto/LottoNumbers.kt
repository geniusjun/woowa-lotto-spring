package com.geniusjun.lotto.domain.lotto

class LottoNumbers private constructor(
    val numbers: List<Int>
) {

    init {
        validate(numbers)
    }

    private fun validate(numbers: List<Int>) {
        validateSize(numbers)
        validateDistinct(numbers)
        validateRange(numbers)
    }

    private fun validateSize(numbers: List<Int>) {
        require(numbers.size == LOTTO_SIZE) {
            "로또 번호는 정확히 ${LOTTO_SIZE}개여야 합니다. (입력된 개수: ${numbers.size})"
        }
    }

    private fun validateDistinct(numbers: List<Int>) {
        require(numbers.distinct().size == LOTTO_SIZE) {
            "로또 번호는 중복될 수 없습니다. (입력된 번호: $numbers)"
        }
    }

    private fun validateRange(numbers: List<Int>) {
        require(numbers.all { it in MIN_NUMBER..MAX_NUMBER }) {
            "로또 번호는 ${MIN_NUMBER}부터 ${MAX_NUMBER} 사이의 숫자여야 합니다. (입력된 번호: $numbers)"
        }
    }

    companion object {
        private const val LOTTO_SIZE = 6
        private const val MIN_NUMBER = 1
        private const val MAX_NUMBER = 45

        fun of(numbers: List<Int>): LottoNumbers {
            return LottoNumbers(numbers.toList()) // 방어적 복사
        }
    }
}
