package com.geniusjun.lotto.presentation.common

import com.geniusjun.lotto.application.auth.exception.InvalidJwtTokenException
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {
    fun currentMemberId(): Long {
        val auth = SecurityContextHolder.getContext().authentication
            ?: throw InvalidJwtTokenException("인증 정보가 없습니다. (로그인이 필요합니다.)")

        return auth.principal as Long
    }
}
