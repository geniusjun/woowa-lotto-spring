package com.geniusjun.lotto.application.fortune

import jakarta.annotation.PostConstruct
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDate

@Component
class FortuneSeedInitializer(
    private val redisTemplate: StringRedisTemplate
) {

    /**
     * 서버 시작 시 실행됨
     * 오늘 날짜의 운세 시드가 없다면 자동 생성
     */
    @PostConstruct
    fun initTodaySeed() {
        val ops = redisTemplate.opsForValue()
        val today = LocalDate.now().toString()
        val key = "fortune:seed:$today"

        if (ops.get(key) == null) {
            val seed = (0..999999).random().toString()
            ops.set(key, seed, Duration.ofDays(1))
        }
    }
}
