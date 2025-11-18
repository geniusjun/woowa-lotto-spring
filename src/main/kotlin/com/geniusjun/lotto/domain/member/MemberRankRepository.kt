package com.geniusjun.lotto.domain.member

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRankRepository : JpaRepository<Member, Long> {

    /**
     * 잔액 기준 Top N 회원 랭킹 조회
     */
    @Query(
        value = """
            SELECT 
                nickname AS nickname,
                balance  AS balance,
                DENSE_RANK() OVER (ORDER BY balance DESC, id ASC) AS rank
            FROM members
            ORDER BY balance DESC, id ASC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun findTopRankers(@Param("limit") limit: Int): List<MemberRankProjection>


    /**
     * 특정 회원의 전체 랭킹 조회
     */
    @Query(
        value = """
            SELECT nickname, balance, rank FROM (
                SELECT 
                    id,
                    nickname,
                    balance,
                    DENSE_RANK() OVER (ORDER BY balance DESC, id ASC) AS rank
                FROM members
            ) ranked
            WHERE ranked.id = :memberId
        """,
        nativeQuery = true
    )
    fun findMemberRank(@Param("memberId") memberId: Long): MemberRankProjection?
}
