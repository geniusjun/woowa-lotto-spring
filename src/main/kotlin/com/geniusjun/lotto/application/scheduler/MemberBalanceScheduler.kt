package com.geniusjun.lotto.application.scheduler

import com.geniusjun.lotto.domain.member.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MemberBalanceScheduler(
    private val memberRepository: MemberRepository
) {

    private val DAILY_REWARD = 100_000L

    /**
     * 매일 00:00에 전체 회원 balance +100,000
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    fun addDailyReward() {
        memberRepository.increaseBalanceForAll(DAILY_REWARD)
    }
}
