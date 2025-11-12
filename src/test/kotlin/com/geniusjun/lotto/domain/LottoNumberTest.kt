package com.geniusjun.lotto.domain

import com.geniusjun.lotto.domain.lotto.exception.InvalidLottoException
import com.geniusjun.lotto.domain.lotto.LottoNumber
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("LottoNumber 도메인 테스트")
class LottoNumberTest {

    @Test
    @DisplayName("번호가 1부터 45 사이면 정상적으로 생성된다")
    fun create_number_within_valid_range() {
        val num = LottoNumber.Companion.of(10)
        assertEquals(10, num.value)
    }

    @Test
    @DisplayName("번호가 1 미만 또는 45 초과이면 예외가 발생한다")
    fun create_number_out_of_range_should_throw_exception() {
        val ex = assertThrows<InvalidLottoException> {
            LottoNumber.Companion.of(0)
        }
    }
}
