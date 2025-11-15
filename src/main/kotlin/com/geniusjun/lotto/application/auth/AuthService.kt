package com.geniusjun.lotto.application.auth

import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import com.geniusjun.lotto.application.auth.exception.RefreshTokenMismatchException
import com.geniusjun.lotto.application.auth.exception.RefreshTokenNotFoundException
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

    /** 구글 로그인 → 회원 조회/생성 + 토큰 발급 */
    @Transactional
    fun loginWithGoogle(idToken: String): LoginResponse {
        // 1) 구글 토큰 검증 (여기서 유효성 검증 끝)
        val payload = googleTokenVerifier.verify(idToken)

        // 2) 회원 조회 또는 생성
        val member = memberRepository.findByGoogleSub(payload.sub)
            ?: memberRepository.save(
                Member.createWithGoogle(
                    nickname = payload.name ?: "user-${System.currentTimeMillis()}",
                    googleSub = payload.sub
                )
            )

        val memberId = member.id
            ?: error("member id is null after save")

        // 3) 토큰 발급
        val access = jwtProvider.createAccessToken(memberId)
        val refresh = jwtProvider.createRefreshToken(memberId)

        // 4) refresh 보관 (Redis)
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

    /** 리프레시 → 새 Access 발급 */
    @Transactional(readOnly = true)
    fun reissueAccessByRefresh(refreshToken: String): ReissueResponse {
        // 1) 먼저 validate → 만료/서명검증
        jwtProvider.validate(refreshToken)

        // 2) 타입 검증 (Access 토큰 들고 온 경우 차단)
        if (jwtProvider.tokenType(refreshToken) != TokenType.REFRESH) {
            throw InvalidJwtTokenException("Refresh 토큰이 아닙니다.")
        }

        // 3) memberId 추출
        val memberId = jwtProvider.extractMemberId(refreshToken)

        // 4) Redis 저장된 refresh 확인
        val saved = refreshStore.get(memberId)
            ?: throw RefreshTokenNotFoundException("리프레시 토큰을 찾을 수 없습니다. (memberId=$memberId)")

        // 5) 일치 확인
        if (saved != refreshToken) {
            throw RefreshTokenMismatchException("리프레시 토큰이 일치하지 않습니다. (memberId=$memberId)")
        }

        // 6) 새 Access 발급
        val newAccess = jwtProvider.createAccessToken(memberId)
        return ReissueResponse(
            accessToken = newAccess,
            accessTokenExpiresIn = jwtProvider.accessExpSeconds(),
            tokenType = "Bearer"
        )
    }

    /** 로그아웃 = refresh 삭제 */
    @Transactional
    fun logout(memberId: Long) {
        refreshStore.delete(memberId)
    }

    data class ReissueResponse(
        val accessToken: String,
        val accessTokenExpiresIn: Long,
        val tokenType: String
    )
}
