package com.geniusjun.lotto.presentation.member

import com.geniusjun.lotto.application.member.MemberRankService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.member.dto.MemberRankResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rank")
class MemberRankController(
    private val memberRankService: MemberRankService
) {

    @GetMapping("/top3")
    fun getTop3(): ApiResponse<List<MemberRankResult>> {
        return ApiResponse.ok(memberRankService.getTop3Ranks())
    }

    @GetMapping("/me")
    fun getMyRank(): ApiResponse<MemberRankResult> {
        return ApiResponse.ok(memberRankService.getMyRank())
    }
}
