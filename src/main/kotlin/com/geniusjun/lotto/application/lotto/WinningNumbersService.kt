package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.LottoNumberGenerator
import com.geniusjun.lotto.domain.lotto.WinningNumbersEntity
import com.geniusjun.lotto.domain.lotto.WinningNumbersRepository
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WinningNumbersService(
    private val winningNumbersRepository: WinningNumbersRepository
) {

    @Transactional
    fun generateWeeklyNumbers() {
        val main = LottoNumberGenerator.generate().toIntList()
        val bonus = (1..45).filterNot { main.contains(it) }.random()

        val entity = winningNumbersRepository.findTop1ByOrderByUpdatedAtDesc()
            ?: WinningNumbersEntity(
                mainNumbers = main.joinToString(","),
                bonusNumber = bonus
            )

        entity.update(main, bonus)
        winningNumbersRepository.save(entity)
    }

    @Transactional(readOnly = true)
    fun getLatest(): WinningNumbersEntity {
        return winningNumbersRepository.findTop1ByOrderByUpdatedAtDesc()
            ?: throw WinningNumbersNotFoundException("등록된 당첨 번호가 없습니다.")
    }
}
