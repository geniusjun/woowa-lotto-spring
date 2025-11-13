package com.geniusjun.lotto.presentation.fortune

import com.geniusjun.lotto.application.fortune.FortuneService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.common.SecurityUtil
import com.geniusjun.lotto.presentation.fortune.dto.FortuneResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FortuneController(
    private val fortuneService: FortuneService
) {

    @GetMapping("/today")
    fun getToday(): ApiResponse<FortuneResponse> {
        val memberId = SecurityUtil.currentMemberId()
        val fortune = fortuneService.getTodayFortune(memberId)
        return ApiResponse.ok(FortuneResponse(fortune))
    }

}
