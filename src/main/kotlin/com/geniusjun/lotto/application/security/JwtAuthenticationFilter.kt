package com.geniusjun.lotto.application.security

import com.geniusjun.lotto.application.auth.JwtProvider
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
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)
        if (token != null && validateToken(token, response)) {
            setAuthentication(token)
        }
        filterChain.doFilter(request, response)
    }

    /** Authorization 헤더에서 Bearer 토큰 추출 */
    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization") ?: return null
        if (!header.startsWith("Bearer ")) return null
        return header.removePrefix("Bearer ").trim()
    }

    /** 토큰 검증 (만료·서명·형식 검사) */
    private fun validateToken(token: String, response: HttpServletResponse): Boolean {
        return try {
            val claims = Jwts.parser()
                .verifyWith(jwtProvider.getKey())   // 서명 키로 검증
                .build()
                .parseSignedClaims(token)
                .payload

            val subject = claims.subject
            if (subject.startsWith("refresh:")) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.write("Access token required, refresh token not allowed.")
                return false
            }
            true
        } catch (e: ExpiredJwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Access token expired")
            false
        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid JWT token")
            false
        } catch (e: Exception) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("JWT processing error")
            false
        }
    }

    /** 인증 객체를 SecurityContext에 저장 */
    private fun setAuthentication(token: String) {
        val claims = Jwts.parser()
            .verifyWith(jwtProvider.getKey())
            .build()
            .parseSignedClaims(token)
            .payload

        val subject = claims.subject
        if (subject.startsWith("refresh:")) return  // refresh는 인증 등록 안함

        val memberId = subject.toLong()
        val authentication = UsernamePasswordAuthenticationToken(
            memberId, null, listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
        SecurityContextHolder.getContext().authentication = authentication
    }
}
