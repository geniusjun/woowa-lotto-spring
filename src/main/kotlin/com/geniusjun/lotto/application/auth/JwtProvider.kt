package com.geniusjun.lotto.application.auth

import com.geniusjun.lotto.application.auth.exception.ExpiredJwtTokenException
import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
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

    /* 1. 토큰 생성 */

    fun createAccessToken(memberId: Long): String {
        val now = Instant.now()
        return Jwts.builder()
            .subject(memberId.toString()) // ACCESS 표시
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(accessExpSeconds)))
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(memberId: Long): String {
        val now = Instant.now()
        return Jwts.builder()
            .subject("refresh:$memberId") // REFRESH 표시
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(refreshExpSeconds)))
            .signWith(key)
            .compact()
    }

    fun accessExpSeconds() = accessExpSeconds
    fun refreshExpSeconds() = refreshExpSeconds
    fun getKey() = key


    /* 2. 토큰 파싱 + 검증 (예외 변환 포함)*/

    @Throws(InvalidJwtTokenException::class)
    fun validate(token: String): Claims =
        try {
            parseClaims(token)
        } catch (e: ExpiredJwtException) {
            throw ExpiredJwtTokenException("토큰이 만료되었습니다.")
        } catch (e: JwtException) {
            throw InvalidJwtTokenException("유효하지 않은 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            throw InvalidJwtTokenException("잘못된 토큰 형식입니다.")
        }

    fun parseClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

    /* 3) 토큰 타입/사용자 식별 정보 */

    fun tokenType(token: String): TokenType {
        val subject = validate(token).subject
        return if (subject.startsWith("refresh:")) TokenType.REFRESH else TokenType.ACCESS
    }

    fun extractSubject(token: String): String =
        validate(token).subject

    fun extractMemberId(token: String): Long {
        val subject = validate(token).subject
        return if (subject.startsWith("refresh:")) {
            subject.removePrefix("refresh:").toLong()
        } else {
            subject.toLong()
        }
    }
}
