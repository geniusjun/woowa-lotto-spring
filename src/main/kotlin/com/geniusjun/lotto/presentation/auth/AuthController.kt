package com.geniusjun.lotto.presentation.auth

import com.geniusjun.lotto.application.auth.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    data class GoogleLoginRequest(val idToken: String)

    @PostMapping("/google")
    fun loginWithGoogle(@RequestBody req: GoogleLoginRequest): ResponseEntity<AuthService.LoginResponse> {
        val result = authService.loginWithGoogle(req.idToken)
        return ResponseEntity.ok(result)
    }
}
