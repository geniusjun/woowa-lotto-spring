package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.WinningNumbersEntity
import com.geniusjun.lotto.domain.lotto.WinningNumbersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WinningNumbersService(
    private val winningNumbersRepository: WinningNumbersRepository
) {

    @Transactional
    fun register(round: Long, mainNumbers: List<Int>, bonusNumber: Int?): Long {
        val exists = winningNumbersRepository.findByRound(round)
        if (exists != null) {
            throw IllegalArgumentException("이미 등록된 회차입니다. round=$round") // 이미 있는 회차면 막기
        }

        val entity = WinningNumbersEntity(
            round = round,
            mainNumbers = mainNumbers,
            bonusNumber = bonusNumber
        )
        return winningNumbersRepository.save(entity).id!!
    }

    @Transactional(readOnly = true)
    fun getLatest(): WinningNumbersEntity? {
        return winningNumbersRepository.findTop1ByOrderByRoundDesc()
    }

    @Transactional(readOnly = true)
    fun getByRound(round: Long): WinningNumbersEntity? {
        return winningNumbersRepository.findByRound(round)
    }
}
