package com.geniusjun.lotto.domain.member

interface MemberRankProjection {
    fun getNickname(): String
    fun getBalance(): Long
    fun getRank(): Long
}
