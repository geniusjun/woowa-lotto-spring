package com.geniusjun.lotto.domain

import com.geniusjun.lotto.domain.lotto.LottoNumberGenerator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("LottoNumberGenerator 테스트")
class LottoNumberGeneratorTest {

    @Test
    @DisplayName("랜덤으로 생성된 번호는 6개이고, 서로 중복되지 않으며, 1~45 범위에 있어야 한다")
    fun generate_valid_numbers() {
        val lotto = LottoNumberGenerator.generate()
        val nums = lotto.values.map { it.value }

        assertEquals(6, nums.size)
        assertEquals(6, nums.distinct().size)
        assertTrue(nums.all { it in 1..45 })
    }

    @Test
    @DisplayName("여러 번 생성해도 항상 규칙을 만족한다")
    fun generate_multiple_times() {
        repeat(20) {
            val lotto = LottoNumberGenerator.generate()
            val nums = lotto.values.map { it.value }

            assertEquals(6, nums.size)
            assertEquals(6, nums.distinct().size)
            assertTrue(nums.all { it in 1..45 })
        }
    }
}
