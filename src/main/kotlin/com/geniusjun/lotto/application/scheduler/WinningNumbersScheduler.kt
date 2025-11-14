package com.geniusjun.lotto.application.scheduler

import com.geniusjun.lotto.application.lotto.WinningNumbersService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WinningNumbersScheduler(
    private val winningNumbersService: WinningNumbersService
) {

    /**
     * 매주 월요일 00:00 자동 번호 갱신
     * cron 표현식: 초 분 시 일 월 요일
     */
    @Scheduled(cron = "0 0 0 * * MON")
    fun generateWeekly() {
        winningNumbersService.generateWeeklyNumbers()
    }
}
