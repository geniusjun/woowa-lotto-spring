package com.geniusjun.lotto.application.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.geniusjun.lotto.application.auth.JwtProvider
import com.geniusjun.lotto.application.auth.TokenType
import com.geniusjun.lotto.application.auth.exception.ExpiredJwtTokenException
import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import com.geniusjun.lotto.presentation.common.ErrorCode
import com.geniusjun.lotto.presentation.common.ErrorResponse
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
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token != null) {
            val claims = try {
                jwtProvider.validate(token)
            } catch (e: ExpiredJwtTokenException) {
                writeError(response, ErrorCode.AUTH_EXPIRED_TOKEN, e.message)
                return
            } catch (e: InvalidJwtTokenException) {
                writeError(response, ErrorCode.AUTH_INVALID_TOKEN, e.message)
                return
            } catch (e: Exception) {
                writeError(response, ErrorCode.INTERNAL_SERVER_ERROR, e.message)
                return
            }

            // refresh token으로 API 요청하는 경우 차단
            if (jwtProvider.tokenType(token) == TokenType.REFRESH) {
                writeError(response, ErrorCode.AUTH_INVALID_TOKEN, "Access 토큰이 필요합니다.")
                return
            }

            setAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization") ?: return null
        if (!header.startsWith("Bearer ")) return null
        return header.removePrefix("Bearer ").trim()
    }

    private fun setAuthentication(token: String) {
        val memberId = jwtProvider.extractMemberId(token)

        val authentication = UsernamePasswordAuthenticationToken(
            memberId,
            null,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun writeError(
        response: HttpServletResponse,
        errorCode: ErrorCode,
        detail: String?
    ) {
        if (response.isCommitted) return

        response.status = errorCode.status.value()
        response.contentType = "application/json;charset=UTF-8"

        val body = ErrorResponse(
            code = errorCode.name,
            message = detail ?: errorCode.message
        )

        response.writer.use { writer ->
            writer.write(objectMapper.writeValueAsString(body))
        }
    }
}
