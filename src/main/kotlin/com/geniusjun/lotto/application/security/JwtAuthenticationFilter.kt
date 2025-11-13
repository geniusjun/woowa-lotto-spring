package com.geniusjun.lotto.application.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.geniusjun.lotto.application.auth.JwtProvider
import com.geniusjun.lotto.presentation.common.ErrorCode
import com.geniusjun.lotto.presentation.common.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper // JSON 전환 Mapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        // 토큰 없으면 그냥 다음 필터로 넘김 (permitAll 구역 포함)
        if (token != null) {
            if (!validateToken(token, response)) {
                // 여기서 이미 에러 응답을 내려보냈으므로 체인 중단
                return
            }
            // 유효한 경우에만 인증 세팅
            setAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }

    /** 1. Authorization 헤더에서 Bearer 토큰 추출 */
    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization") ?: return null
        if (!header.startsWith("Bearer ")) return null
        return header.removePrefix("Bearer ").trim()
    }

    /** 2. 토큰 검증 (만료·서명·형식 검사) + 에러 JSON 응답 */
    private fun validateToken(token: String, response: HttpServletResponse): Boolean {
        return try {
            val claims = Jwts.parser()
                .verifyWith(jwtProvider.getKey())
                .build()
                .parseSignedClaims(token)
                .payload

            val subject = claims.subject
            if (subject.startsWith("refresh:")) {
                writeError(response, ErrorCode.AUTH_INVALID_TOKEN, "Access 토큰이 아닌 토큰입니다.")
                false
            } else {
                true
            }
        } catch (e: ExpiredJwtException) {
            writeError(response, ErrorCode.AUTH_EXPIRED_TOKEN, e.message)
            false
        } catch (e: JwtException) {
            writeError(response, ErrorCode.AUTH_INVALID_TOKEN, e.message)
            false
        } catch (e: Exception) {
            // 혹시 모를 예외는 서버 에러로 통일
            writeError(response, ErrorCode.INTERNAL_SERVER_ERROR, e.message)
            false
        }
    }

    /** 3. 인증 객체를 SecurityContext에 저장 */
    private fun setAuthentication(token: String) {
        val claims = Jwts.parser()
            .verifyWith(jwtProvider.getKey())
            .build()
            .parseSignedClaims(token)
            .payload

        val subject = claims.subject
        if (subject.startsWith("refresh:")) {
            // 이 경우는 validate 단계에서 이미 막혀야 하지만,
            // 방어적으로 한 번 더 체크
            return
        }

        val memberId = subject.toLong()
        val authentication = UsernamePasswordAuthenticationToken(
            memberId,
            null,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    /** 공통 에러 응답 JSON 쓰는 헬퍼 */
    private fun writeError(
        response: HttpServletResponse,
        errorCode: ErrorCode,
        detailMessage: String?
    ) {
        if (response.isCommitted) return  // 이미 응답이 나갔으면 건드리지 않음

        response.status = errorCode.status.value()
        response.contentType = "application/json;charset=UTF-8"

        val body = ErrorResponse(
            code = errorCode.name,
            message = detailMessage ?: errorCode.message
        )

        response.writer.use { writer ->
            writer.write(objectMapper.writeValueAsString(body))
        }
    }
}
