package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.WinningNumbersEntity
import com.geniusjun.lotto.domain.lotto.WinningNumbersRepository
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersDuplicateException
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersNotFoundException
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
            throw WinningNumbersDuplicateException("이미 등록된 회차입니다. (round=$round)")
        }

        val entity = WinningNumbersEntity(
            round = round,
            mainNumbers = mainNumbers,
            bonusNumber = bonusNumber
        )
        return winningNumbersRepository.save(entity).id!!
    }

    @Transactional(readOnly = true)
    fun getLatest(): WinningNumbersEntity {
        return winningNumbersRepository.findTop1ByOrderByRoundDesc()
            ?: throw WinningNumbersNotFoundException("등록된 당첨 번호가 없습니다.")
    }

    @Transactional(readOnly = true)
    fun getByRound(round: Long): WinningNumbersEntity {
        return winningNumbersRepository.findByRound(round)
            ?: throw WinningNumbersNotFoundException("해당 회차의 당첨 번호가 없습니다. (round=$round)")
    }
}
