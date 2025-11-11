package com.geniusjun.lotto.domain.lotto

import org.springframework.data.jpa.repository.JpaRepository

interface WinningNumbersRepository : JpaRepository<WinningNumbersEntity, Long> {
    fun findByRound(round: Long): WinningNumbersEntity?
    fun findTop1ByOrderByRoundDesc(): WinningNumbersEntity?
}
