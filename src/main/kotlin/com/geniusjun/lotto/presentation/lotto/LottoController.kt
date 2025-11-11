package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.LottoService
import com.geniusjun.lotto.presentation.lotto.dto.LottoGenerateResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lotto")
class LottoController(
    private val lottoService: LottoService
) {

    @PostMapping("/generate")
    fun generate(): LottoGenerateResponse {
        val numbers = lottoService.generateLottoNumbers()
        return LottoGenerateResponse(numbers = numbers)
    }
}
