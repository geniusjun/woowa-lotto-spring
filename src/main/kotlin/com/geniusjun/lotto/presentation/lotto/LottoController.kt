package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.LottoDrawService
import com.geniusjun.lotto.presentation.lotto.dto.LottoDrawResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lotto")
class LottoController(
    private val lottoDrawService: LottoDrawService
) {

    @PostMapping("/draw")
    fun draw(@RequestParam memberId: Long): LottoDrawResponse {
        return lottoDrawService.drawForMember(memberId)
    }
}
