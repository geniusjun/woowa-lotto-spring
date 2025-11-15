package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.WinningNumbersService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.lotto.dto.WinningNumbersResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/winning")
class WinningNumbersController(
    private val winningNumbersService: WinningNumbersService
) {

    /** 최신 당첨 번호 */
    @GetMapping("/latest")
    fun getLatest(): ApiResponse<WinningNumbersResponse> {

        val entity = winningNumbersService.getLatest()

        val response = WinningNumbersResponse(
            mainNumbers = entity.mainNumbersAsList(),
            bonusNumber = entity.bonusNumber
        )

        return ApiResponse.ok(response)
    }
}
