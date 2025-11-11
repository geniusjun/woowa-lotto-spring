package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.LottoNumber
import com.geniusjun.lotto.domain.lotto.LottoNumberGenerator
import org.springframework.stereotype.Service

@Service
class LottoService {

    fun generateLottoNumbers(): List<LottoNumber> {
        val lotto = LottoNumberGenerator.generate()
        return lotto.values
    }
}
