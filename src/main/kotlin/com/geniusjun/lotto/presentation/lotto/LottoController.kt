package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.LottoDrawService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.common.SecurityUtil
import com.geniusjun.lotto.presentation.lotto.dto.LottoDrawResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lotto")
class LottoController(
    private val lottoDrawService: LottoDrawService
) {

    /** 랜덤 로또 1회 구매 */
    @PostMapping("/draw")
    fun draw(): ApiResponse<LottoDrawResponse> {
        val memberId = SecurityUtil.currentMemberId()
        val result = lottoDrawService.drawForMember(memberId)
        return ApiResponse.ok(result)
    }
}
