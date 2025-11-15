package com.geniusjun.lotto.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByNickname(nickname: String): Member?
    fun findByGoogleSub(googleSub: String): Member?

    @Modifying
    @Query("UPDATE Member m SET m.balance = m.balance + :amount")
    fun increaseBalanceForAll(amount: Long)
}
