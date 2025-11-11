package com.geniusjun.lotto.domain

import com.geniusjun.lotto.domain.lotto.InvalidLottoException
import com.geniusjun.lotto.domain.lotto.LottoNumbers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("LottoNumbers 도메인 테스트")
class LottoNumbersTest {

    @Test
    @DisplayName("번호가 6개가 아니면 예외가 발생한다")
    fun not_six_numbers_throws_exception() {
        val ex = assertThrows<InvalidLottoException> {
            LottoNumbers.of(listOf(1, 2, 3, 4, 5))
        }
        assertTrue(ex.message!!.contains("6개"))
    }

    @Test
    @DisplayName("번호가 중복되면 예외가 발생한다")
    fun duplicate_numbers_throws_exception() {
        val ex = assertThrows<InvalidLottoException> {
            LottoNumbers.of(listOf(1, 2, 3, 3, 4, 5))
        }
        assertTrue(ex.message!!.contains("중복"))
    }

    @Test
    @DisplayName("정상적인 번호면 생성된다")
    fun valid_numbers_create_successfully() {
        val lotto = LottoNumbers.of(listOf(1, 2, 3, 4, 5, 6))
        assertEquals(6, lotto.values.size)
        assertEquals(listOf(1, 2, 3, 4, 5, 6), lotto.values.map { it.value })
    }
}
