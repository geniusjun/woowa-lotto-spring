package com.geniusjun.lotto.domain.lotto

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("로또 당첨 결과 계산 테스트")
class LottoMatcherTest {

    @Test
    @DisplayName("6개 모두 맞으면 1등이다")
    fun first_prize() {
        val my = LottoNumbers.of(listOf(1, 2, 3, 4, 5, 6))
        val winning = DrawResult.of(listOf(1, 2, 3, 4, 5, 6))

        val result = LottoMatcher.match(my, winning)

        assertEquals(6, result.matchCount)
        assertFalse(result.bonusMatched)
        assertEquals(LottoRank.FIRST, result.rank)
    }

    @Test
    @DisplayName("5개 + 보너스 맞으면 2등이다")
    fun second_prize() {
        val my = LottoNumbers.of(listOf(1, 2, 3, 4, 5, 7))
        val winning = DrawResult.of(
            mainNumbers = listOf(1, 2, 3, 4, 5, 6),
            bonus = 7
        )

        val result = LottoMatcher.match(my, winning)

        assertEquals(5, result.matchCount)
        assertTrue(result.bonusMatched)
        assertEquals(LottoRank.SECOND, result.rank)
    }

    @Test
    @DisplayName("5개만 맞으면 3등이다")
    fun third_prize() {
        val my = LottoNumbers.of(listOf(1, 2, 3, 4, 5, 40))
        val winning = DrawResult.of(listOf(1, 2, 3, 4, 5, 6))

        val result = LottoMatcher.match(my, winning)

        assertEquals(5, result.matchCount)
        assertFalse(result.bonusMatched)
        assertEquals(LottoRank.THIRD, result.rank)
    }

    @Test
    @DisplayName("3개 맞으면 5등이다")
    fun fifth_prize() {
        val my = LottoNumbers.of(listOf(1, 2, 3, 40, 41, 42))
        val winning = DrawResult.of(listOf(1, 2, 3, 4, 5, 6))

        val result = LottoMatcher.match(my, winning)

        assertEquals(3, result.matchCount)
        assertEquals(LottoRank.FIFTH, result.rank)
        assertEquals(5_000L, result.reward)
    }

    @Test
    @DisplayName("2개 이하면 꽝이다")
    fun none_prize() {
        val my = LottoNumbers.of(listOf(40, 41, 42, 43, 44, 45))
        val winning = DrawResult.of(listOf(1, 2, 3, 4, 5, 6))

        val result = LottoMatcher.match(my, winning)

        assertEquals(0, result.matchCount)
        assertEquals(LottoRank.NONE, result.rank)
        assertEquals(0L, result.reward)
    }
}
