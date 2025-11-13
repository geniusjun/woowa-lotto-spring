package com.geniusjun.lotto.application.auth

import com.google.auth.oauth2.TokenVerifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GoogleTokenVerifier(
    @Value("\${google.client-id}") private val googleClientId: String
) {
    private val verifier: TokenVerifier = TokenVerifier.newBuilder()
        .setAudience(googleClientId)                       // aud 검증
        .setIssuer("https://accounts.google.com")         // iss 검증
        .build()

    data class GoogleUserPayload(
        val sub: String,          // 구글 유저 고유 ID (String)
        val email: String?,       // 선택적
        val emailVerified: Boolean?,
        val name: String?,        // 선택적
        val picture: String?      // 선택적
    )

    /**
     * Google ID Token(JWT)을 검증하고, 주요 클레임을 돌려줍니다.
     * - 서명/만료/iss/aud 모두 검증됨
     * - 검증 실패 시 IllegalArgumentException
     */
    fun verify(idToken: String): GoogleUserPayload {
        val token = verifier.verify(idToken)
            ?: throw IllegalArgumentException("Invalid Google ID token")

        val claims = token.payload
        val emailVerified = (claims["email_verified"] as? Boolean)
            ?: (claims["email_verified"] as? String)?.toBooleanStrictOrNull()

        return GoogleUserPayload(
            sub = claims["sub"] as String,
            email = claims["email"] as? String,
            emailVerified = emailVerified,
            name = claims["name"] as? String,
            picture = claims["picture"] as? String
        )
    }

}
