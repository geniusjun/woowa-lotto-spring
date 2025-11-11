package com.geniusjun.lotto.presentation.fortune

import com.geniusjun.lotto.application.fortune.FortuneService
import com.geniusjun.lotto.presentation.fortune.dto.FortuneResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FortuneController(
    private val fortuneService: FortuneService
) {

    // 임시로 memberId를 쿼리스트링으로 받기. 나중에 토큰 생기면 거기서 꺼내오기
    @GetMapping("/fortune/today")
    fun getToday(@RequestParam memberId: Long): FortuneResponse {
        val fortune = fortuneService.getTodayFortune(memberId)
        return FortuneResponse(fortune);
    }
}
