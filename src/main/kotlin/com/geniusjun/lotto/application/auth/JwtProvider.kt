package com.geniusjun.lotto.application.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*

@Component
class JwtProvider(
    @Value("\${security.jwt.secret}") secret: String,
    @Value("\${security.jwt.access-exp-seconds}") private val accessExpSeconds: Long,
    @Value("\${security.jwt.refresh-exp-seconds}") private val refreshExpSeconds: Long
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun createAccessToken(memberId: Long): String {
        val now = Instant.now()
        return Jwts.builder()
            .subject(memberId.toString())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessExpSeconds)))
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(memberId: Long): String {
        val now = Instant.now()
        return Jwts.builder()
            .subject("refresh:$memberId")
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(refreshExpSeconds)))
            .signWith(key)
            .compact()
    }

    fun accessExpSeconds() = accessExpSeconds
    fun refreshExpSeconds() = refreshExpSeconds
}
