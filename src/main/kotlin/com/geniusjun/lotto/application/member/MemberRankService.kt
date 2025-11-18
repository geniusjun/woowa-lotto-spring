package com.geniusjun.lotto.application.member

import com.geniusjun.lotto.domain.member.MemberRankRepository
import com.geniusjun.lotto.domain.member.exception.MemberNotFoundException
import com.geniusjun.lotto.presentation.common.SecurityUtil
import com.geniusjun.lotto.presentation.member.dto.MemberRankResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberRankService(
    private val memberRankRepository: MemberRankRepository
) {

    @Transactional(readOnly = true)
    fun getTop3Ranks(): List<MemberRankResult> {
        val topRankers = memberRankRepository.findTopRankers(3)
        return topRankers.map {
            MemberRankResult(
                nickname = it.getNickname(),
                balance = it.getBalance(),
                rank = it.getRank()
            )
        }
    }

    @Transactional(readOnly = true)
    fun getMyRank(): MemberRankResult {
        val memberId = SecurityUtil.currentMemberId()

        val result = memberRankRepository.findMemberRank(memberId)
            ?: throw MemberNotFoundException("회원을 찾을 수 없습니다. id=$memberId")

        return MemberRankResult(
            nickname = result.getNickname(),
            balance = result.getBalance(),
            rank = result.getRank()
        )
    }
}
