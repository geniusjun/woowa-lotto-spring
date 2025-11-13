package com.geniusjun.lotto.presentation.auth

import com.geniusjun.lotto.application.auth.AuthService
import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import com.geniusjun.lotto.presentation.common.ApiResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    data class GoogleLoginRequest(val idToken: String)
    data class RefreshRequest(val refreshToken: String)

    /** Google 로그인 */
    @PostMapping("/google")
    fun loginWithGoogle(@RequestBody req: GoogleLoginRequest): ApiResponse<AuthService.LoginResponse> {
        val result = authService.loginWithGoogle(req.idToken)
        return ApiResponse.ok(result)
    }

    /** Refresh → 새 Access 발급 */
    @PostMapping("/refresh")
    fun refresh(@RequestBody req: RefreshRequest): ApiResponse<AuthService.ReissueResponse> {
        val result = authService.reissueAccessByRefresh(req.refreshToken)
        return ApiResponse.ok(result)
    }

    /** 로그아웃 */
    @DeleteMapping("/logout")
    fun logout(): ApiResponse<String> {
        val memberId = currentMemberId()
        authService.logout(memberId)
        return ApiResponse.ok("LOGOUT_OK")
    }

    /** SecurityContext에서 memberId 추출 */
    private fun currentMemberId(): Long {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw InvalidJwtTokenException("인증 정보가 없습니다. (로그인이 필요합니다.)")

        return auth.principal as Long
    }
}
