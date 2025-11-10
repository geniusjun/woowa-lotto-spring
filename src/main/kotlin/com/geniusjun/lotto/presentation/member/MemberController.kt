package com.geniusjun.lotto.presentation.member

import com.geniusjun.lotto.application.member.MemberService
import com.geniusjun.lotto.presentation.member.dto.MemberCreateRequest
import com.geniusjun.lotto.presentation.member.dto.MemberResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: MemberCreateRequest): MemberResponse {
        val member = memberService.create(request.nickname)
        return MemberResponse(
            id = member.id!!,
            nickname = member.nickname,
            balance = member.balance
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): MemberResponse {
        val member = memberService.get(id)
        return MemberResponse(
            id = member.id!!,
            nickname = member.nickname,
            balance = member.balance
        )
    }
}
