package com.geniusjun.lotto.presentation.member

import com.geniusjun.lotto.application.member.MemberService
import com.geniusjun.lotto.presentation.common.ApiResponse
import com.geniusjun.lotto.presentation.member.dto.MemberCreateRequest
import com.geniusjun.lotto.presentation.member.dto.MemberResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService
) {

    /** 회원 생성 (테스트/개발용) */
    @PostMapping
    fun create(@RequestBody request: MemberCreateRequest): ApiResponse<MemberResponse> {
        val member = memberService.create(request.nickname)

        val response = MemberResponse(
            id = member.id!!,
            nickname = member.nickname,
            balance = member.balance
        )

        return ApiResponse.ok(response)
    }

    /** 회원 단건 조회 */
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ApiResponse<MemberResponse> {
        val member = memberService.get(id)

        val response = MemberResponse(
            id = member.id!!,
            nickname = member.nickname,
            balance = member.balance
        )

        return ApiResponse.ok(response)
    }
}
