package com.geniusjun.lotto.application.fortune

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class FortuneService(
    private val redisTemplate: StringRedisTemplate
) {

    fun getTodayFortune(memberId: Long): String {
        val key = buildKey(memberId)
        val ops = redisTemplate.opsForValue()

        // 이미 있으면 그거 반환
        val cached = ops.get(key)
        if (cached != null) {
            return cached
        }

        // 없으면 새로 만들기
        val fortune = pickRandomFortune()

        ops.set(key, fortune, Duration.ofHours(24))
        return fortune
    }

    private fun buildKey(memberId: Long): String {
        val today = java.time.LocalDate.now().toString()
        return "fortune:$memberId:$today"
    }

    private fun pickRandomFortune(): String {
        val candidates = listOf(
            "오늘은 로또가 잘 맞을지도?",
            "쉬어가도 되는 날이에요",
            "재화가 들어오는 날이에요!",
            "오늘은 집안에 꼭 붙어있어요."
        )
        return candidates.random()
    }
}
