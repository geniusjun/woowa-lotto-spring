package com.geniusjun.lotto.domain.lotto

class LottoNumbers private constructor(
    val numbers: List<Int>
) {

    init {
        require(numbers.size == LOTTO_SIZE) {
            "Lotto must have exactly $LOTTO_SIZE numbers. given=${numbers.size}"
        }
        require(numbers.distinct().size == LOTTO_SIZE) {
            "Lotto numbers must be distinct. given=$numbers"
        }
        require(numbers.all { it in MIN_NUMBER..MAX_NUMBER }) {
            "Lotto numbers must be between $MIN_NUMBER and $MAX_NUMBER. given=$numbers"
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
