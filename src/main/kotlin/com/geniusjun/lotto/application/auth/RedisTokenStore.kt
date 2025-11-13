package com.geniusjun.lotto.application.auth

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RefreshTokenStore(
    private val redisTemplate: StringRedisTemplate,
    private val jwtProvider: JwtProvider
) {
    private fun key(memberId: Long) = "refresh:$memberId"

    fun save(memberId: Long, refreshToken: String) {
        redisTemplate.opsForValue().set(
            key(memberId),
            refreshToken,
            Duration.ofSeconds(jwtProvider.refreshExpSeconds())
        )
    }

    fun get(memberId: Long): String? = redisTemplate.opsForValue().get(key(memberId))

    fun delete(memberId: Long) { redisTemplate.delete(key(memberId)) }
}
