package com.geniusjun.lotto.application.fortune

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate

@Service
class FortuneService(
    private val redisTemplate: StringRedisTemplate
) {

    companion object {
        private const val CACHE_HOURS = 24L

        private val CANDIDATES = listOf(
            "오늘은 로또가 잘 맞을지도?",
            "쉬어가도 되는 날이에요",
            "재화가 들어오는 날이에요!",
            "오늘은 집안에 꼭 붙어있어요."
        )
    }

    fun getTodayFortune(memberId: Long): String {
        val key = buildKey(memberId)
        val ops = redisTemplate.opsForValue()

        // 캐시가 있으면 그대로 반환
        ops.get(key)?.let { return it }

        // 새 운세 생성
        val fortune = pickRandomFortune()

        ops.set(key, fortune, Duration.ofHours(CACHE_HOURS))
        return fortune
    }

    private fun buildKey(memberId: Long): String {
        val today = LocalDate.now()
        return "fortune:$memberId:$today"
    }

    private fun pickRandomFortune(): String =
        CANDIDATES.random()
}
