package com.geniusjun.lotto.domain.lotto

import org.springframework.data.jpa.repository.JpaRepository

interface WinningNumbersRepository : JpaRepository<WinningNumbersEntity, Long> {
    fun findTop1ByOrderByUpdatedAtDesc(): WinningNumbersEntity?
}

