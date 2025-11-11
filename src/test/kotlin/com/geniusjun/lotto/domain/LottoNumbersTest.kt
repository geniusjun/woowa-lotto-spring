package com.geniusjun.lotto.domain.lotto

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("LottoNumbers 도메인 단위 테스트")
class LottoNumbersTest {

    @Test
    @DisplayName("번호가 6개가 아니면 예외가 발생한다")
    fun `로또 번호는 정확히 6개여야 한다`() {
        val exception = assertThrows<InvalidLottoException> {
            LottoNumbers.of(listOf(1, 2, 3, 4, 5))
        }
        assertTrue(exception.message!!.contains("6개"))
    }

    @Test
    @DisplayName("번호가 중복되면 예외가 발생한다")
    fun `로또 번호는 중복될 수 없다`() {
        val exception = assertThrows<InvalidLottoException> {
            LottoNumbers.of(listOf(1, 2, 3, 3, 4, 5))
        }
        assertTrue(exception.message!!.contains("중복"))
    }

    @Test
    @DisplayName("번호가 1~45 범위를 벗어나면 예외가 발생한다")
    fun `로또 번호는 1부터 45 사이여야 한다`() {
        val exception = assertThrows<InvalidLottoException> {
            LottoNumbers.of(listOf(0, 2, 3, 4, 5, 6))
        }
        assertTrue(exception.message!!.contains("1부터 45 사이"))
    }

    @Test
    @DisplayName("정상적인 번호면 예외 없이 생성된다")
    fun `정상적인 번호로 로또 생성 성공`() {
        val numbers = LottoNumbers.of(listOf(1, 2, 3, 4, 5, 6))
        assertEquals(6, numbers.numbers.size)
        assertEquals(listOf(1, 2, 3, 4, 5, 6), numbers.numbers)
    }
}
