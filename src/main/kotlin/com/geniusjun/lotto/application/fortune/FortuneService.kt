package com.geniusjun.lotto.application.fortune

import com.geniusjun.lotto.domain.fortune.FortuneMessageRepository
import com.geniusjun.lotto.domain.fortune.error.FortuneInvalidStateException
import com.geniusjun.lotto.domain.fortune.error.FortuneNotFoundException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class FortuneService(
    private val redisTemplate: StringRedisTemplate,
    private val fortuneMessageRepository: FortuneMessageRepository
) {

    fun getTodayFortune(memberId: Long): String {
        val today = LocalDate.now().toString()
        val key = "fortune:seed:$today"

        val ops = redisTemplate.opsForValue()

        val seedValue = ops.get(key)
            ?: throw FortuneInvalidStateException("오늘의 운세 시드가 Redis에 존재하지 않습니다. key=$key")

        val seed = seedValue.toIntOrNull()
            ?: throw FortuneInvalidStateException("운세 시드 값이 정수가 아닙니다. value=$seedValue")

        val messages = fortuneMessageRepository.findAll()
        if (messages.isEmpty()) {
            throw FortuneNotFoundException("운세 메시지가 존재하지 않습니다. 관리자 확인 필요.")
        }

        val idx = ((seed + (memberId % messages.size)) % messages.size).toInt()
        return messages[idx].message
    }
}
