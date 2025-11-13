package com.geniusjun.lotto.application.auth

import com.geniusjun.lotto.domain.member.Member
import com.geniusjun.lotto.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val googleTokenVerifier: GoogleTokenVerifier,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun loginWithGoogle(idToken: String): Member {
        // 1. 구글 토큰 검증
        val payload = googleTokenVerifier.verify(idToken)

        // 2. 이미 가입된 구글 사용자 확인
        val existing = memberRepository.findByGoogleSub(payload.sub)
        if (existing != null) return existing

        // 3. 신규 회원 생성
        val newMember = Member(
            nickname = payload.name ?: "사용자${System.currentTimeMillis()}",
            balance = 0L,
            googleSub = payload.sub
        )

        return memberRepository.save(newMember)
    }
}
