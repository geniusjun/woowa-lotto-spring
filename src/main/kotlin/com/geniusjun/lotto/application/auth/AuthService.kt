package com.geniusjun.lotto.application.auth

import com.geniusjun.lotto.domain.member.Member
import com.geniusjun.lotto.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val googleTokenVerifier: GoogleTokenVerifier,
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider,
    private val refreshStore: RefreshTokenStore
) {
    data class LoginResponse(
        val memberId: Long,
        val nickname: String,
        val accessToken: String,
        val accessTokenExpiresIn: Long,
        val refreshToken: String,
        val refreshTokenExpiresIn: Long,
        val tokenType: String = "Bearer"
    )

    @Transactional
    fun loginWithGoogle(idToken: String): LoginResponse {
        // 1) 구글 ID 토큰 검증
        val payload = googleTokenVerifier.verify(idToken)

        // 2) 회원 조회/생성 (googleSub 기반)
        val member = memberRepository.findByGoogleSub(payload.sub)
            ?: memberRepository.save(
                Member(
                    nickname = payload.name ?: "user-${System.currentTimeMillis()}",
                    balance = 0L,
                    googleSub = payload.sub
                )
            )

        val memberId = member.id ?: error("member id is null after save")

        // 3) 토큰 발급
        val access = jwtProvider.createAccessToken(memberId)
        val refresh = jwtProvider.createRefreshToken(memberId)

        // 4) Refresh를 Redis에 저장(덮어쓰기 정책)
        refreshStore.save(memberId, refresh)

        return LoginResponse(
            memberId = memberId,
            nickname = member.nickname,
            accessToken = access,
            accessTokenExpiresIn = jwtProvider.accessExpSeconds(),
            refreshToken = refresh,
            refreshTokenExpiresIn = jwtProvider.refreshExpSeconds()
        )
    }

    /** 리프레시로 액세스 재발급 (나중에 라우트 붙일 예정) */
    @Transactional(readOnly = true)
    fun reissueAccess(memberId: Long, providedRefresh: String): String {
        val saved = refreshStore.get(memberId) ?: throw IllegalArgumentException("refresh not found")
        require(saved == providedRefresh) { "refresh mismatch" }

        return jwtProvider.createAccessToken(memberId)
    }

    @Transactional
    fun logout(memberId: Long) {
        refreshStore.delete(memberId)
    }
}
