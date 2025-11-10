package com.geniusjun.lotto.application.member

import com.geniusjun.lotto.domain.member.Member
import com.geniusjun.lotto.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun create(nickname: String): Member {
        // 임시 간단 검증: 닉네임 중복이면 예외
        memberRepository.findByNickname(nickname)?.let {
            throw IllegalArgumentException("이름이 이미 존재합니다: $nickname")
        }

        val member = Member(nickname = nickname)
        return memberRepository.save(member)
    }

    @Transactional(readOnly = true)
    fun get(id: Long): Member {
        return memberRepository.findById(id)
            .orElseThrow { NoSuchElementException("이름을 찾을 수 없습니다: $id") }
    }
}
