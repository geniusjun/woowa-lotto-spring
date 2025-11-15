package com.geniusjun.lotto.application.member

import com.geniusjun.lotto.domain.member.Member
import com.geniusjun.lotto.domain.member.MemberRepository
import com.geniusjun.lotto.domain.member.exception.DuplicateNicknameException
import com.geniusjun.lotto.domain.member.exception.MemberNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun create(nickname: String): Member {
        memberRepository.findByNickname(nickname)?.let {
            throw DuplicateNicknameException("이미 존재하는 닉네임입니다. (nickname=$nickname)")
        }

        val member = Member(nickname = nickname)
        return memberRepository.save(member)
    }

    @Transactional(readOnly = true)
    fun get(id: Long): Member {
        return memberRepository.findById(id)
            .orElseThrow { MemberNotFoundException("회원 정보를 찾을 수 없습니다. (id=$id)") }
    }

    @Transactional(readOnly = true)
    fun getMyBalance(memberId: Long): Long {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberNotFoundException("회원을 찾을 수 없습니다. (id=$memberId)") }

        return member.balance
    }

}
