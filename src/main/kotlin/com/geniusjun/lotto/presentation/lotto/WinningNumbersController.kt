package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.WinningNumbersService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.lotto.dto.WinningNumbersRequest
import com.geniusjun.lotto.presentation.lotto.dto.WinningNumbersResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/winning")
class WinningNumbersController(
    private val winningNumbersService: WinningNumbersService
) {

    /** 당첨 번호 등록 */
    @PostMapping
    fun register(@RequestBody request: WinningNumbersRequest): ApiResponse<WinningNumbersResponse> {
        winningNumbersService.register(
            request.round,
            request.mainNumbers,
            request.bonusNumber
        )

        val response = WinningNumbersResponse(
            round = request.round,
            mainNumbers = request.mainNumbers,
            bonusNumber = request.bonusNumber
        )

        return ApiResponse.ok(response)
    }

    /** 최신 회차 조회 */
    @GetMapping("/latest")
    fun getLatest(): ApiResponse<WinningNumbersResponse> {
        val entity = winningNumbersService.getLatest()

        val response = WinningNumbersResponse(
            round = entity.round,
            mainNumbers = entity.mainNumbers,
            bonusNumber = entity.bonusNumber
        )

        return ApiResponse.ok(response)
    }

    /** 특정 회차 조회 */
    @GetMapping("/{round}")
    fun getByRound(@PathVariable round: Long): ApiResponse<WinningNumbersResponse> {
        val entity = winningNumbersService.getByRound(round)

        val response = WinningNumbersResponse(
            round = entity.round,
            mainNumbers = entity.mainNumbers,
            bonusNumber = entity.bonusNumber
        )

        return ApiResponse.ok(response)
    }
}
