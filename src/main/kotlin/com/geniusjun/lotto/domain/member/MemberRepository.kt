package com.geniusjun.lotto.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByNickname(nickname: String): Member?
    fun findByGoogleSub(googleSub: String): Member?
}
