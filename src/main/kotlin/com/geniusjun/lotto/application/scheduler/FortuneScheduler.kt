package com.geniusjun.lotto.application.scheduler

import com.geniusjun.lotto.domain.fortune.FortuneMessageRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDate
import kotlin.random.Random

@Component
class FortuneScheduler(
    private val redisTemplate: StringRedisTemplate,
    private val fortuneMessageRepository: FortuneMessageRepository
) {
    /**
     * 매일 00:00에 오늘의 운세 시드값 생성
     */
    @Scheduled(cron = "0 0 0 * * *")
    fun generateDailySeed() {
        val today = LocalDate.now().toString()
        val key = "fortune:seed:$today"

        val ops = redisTemplate.opsForValue()

        if (redisTemplate.hasKey(key)){
            return
        }

        val messagesCount = fortuneMessageRepository.count().toInt()
        val seed = Random.nextInt(messagesCount)

        ops.set(key, seed.toString(), Duration.ofDays(1))
    }

}
