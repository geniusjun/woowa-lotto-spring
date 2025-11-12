package com.geniusjun.lotto.presentation.lotto

import com.geniusjun.lotto.application.lotto.WinningNumbersService
import com.geniusjun.lotto.domain.lotto.exception.InvalidLottoException
import com.geniusjun.lotto.presentation.lotto.dto.WinningNumbersRequest
import com.geniusjun.lotto.presentation.lotto.dto.WinningNumbersResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/winning")
class WinningNumbersController(
    private val winningNumbersService: WinningNumbersService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: WinningNumbersRequest): WinningNumbersResponse {
        winningNumbersService.register(
            request.round,
            request.mainNumbers,
            request.bonusNumber
        )
        return WinningNumbersResponse(
            round = request.round,
            mainNumbers = request.mainNumbers,
            bonusNumber = request.bonusNumber
        )
    }

    @GetMapping("/latest")
    fun getLatest(): WinningNumbersResponse {
        val entity = winningNumbersService.getLatest()
            ?: throw InvalidLottoException("등록된 당첨 번호가 없습니다.")
        return WinningNumbersResponse(
            round = entity.round,
            mainNumbers = entity.mainNumbers,
            bonusNumber = entity.bonusNumber
        )
    }

    @GetMapping("/{round}")
    fun getByRound(@PathVariable round: Long): WinningNumbersResponse {
        val entity = winningNumbersService.getByRound(round)
            ?: throw InvalidLottoException("해당 회차의 당첨 번호가 없습니다. round=$round")
        return WinningNumbersResponse(
            round = entity.round,
            mainNumbers = entity.mainNumbers,
            bonusNumber = entity.bonusNumber
        )
    }
}
