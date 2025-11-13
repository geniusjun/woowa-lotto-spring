package com.geniusjun.lotto.presentation.auth

import com.geniusjun.lotto.application.auth.AuthService
import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    data class GoogleLoginRequest(val idToken: String)
    data class RefreshRequest(val refreshToken: String)

    @PostMapping("/google")
    fun loginWithGoogle(@RequestBody req: GoogleLoginRequest): ResponseEntity<AuthService.LoginResponse> {
        val result = authService.loginWithGoogle(req.idToken)
        return ResponseEntity.ok(result)
    }

    /** 리프레시로 액세스 재발급 (비인증 엔드포인트) */
    @PostMapping("/refresh")
    fun refresh(@RequestBody req: RefreshRequest): ResponseEntity<AuthService.ReissueResponse> {
        val result = authService.reissueAccessByRefresh(req.refreshToken)
        return ResponseEntity.ok(result)
    }

    /** 로그아웃 (인증 필요) — Access 토큰으로 인증된 사용자만 접근 */
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Void> {
        val memberId = currentMemberId()   // 아래 헬퍼 참고
        authService.logout(memberId)
        return ResponseEntity.noContent().build()
    }

    /** SecurityContext에서 memberId 꺼내는 헬퍼 */
    private fun currentMemberId(): Long {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw InvalidJwtTokenException("인증 정보가 없습니다. (로그인이 필요합니다.)")

        return auth.principal as Long
    }
}
