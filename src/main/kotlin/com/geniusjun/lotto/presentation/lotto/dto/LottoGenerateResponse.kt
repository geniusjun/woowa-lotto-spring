package com.geniusjun.lotto.presentation.lotto.dto

import com.geniusjun.lotto.domain.lotto.LottoNumber

data class LottoGenerateResponse(
    val numbers: List<LottoNumber>
)