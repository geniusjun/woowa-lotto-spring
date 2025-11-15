package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.LottoNumberGenerator
import com.geniusjun.lotto.domain.lotto.WinningNumbersEntity
import com.geniusjun.lotto.domain.lotto.WinningNumbersRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class WinningNumbersInitializer(
    private val winningNumbersRepository: WinningNumbersRepository
) {

    /**
     * 서버 시작 시 실행됨
     * DB에 당첨 번호가 없다면
     * 랜덤 6개(main) + 겹치지 않는 보너스 1개 생성
     */
    @PostConstruct
    fun initWinningNumbers() {
        if (winningNumbersRepository.count() > 0) {
            return
        }

        // 1) 메인 번호 6개 생성
        val mainNumbers = LottoNumberGenerator.generate().toIntList()

        // 2) 메인 번호와 겹치지 않는 보너스 번호 생성
        val bonus = (1..45)
            .filterNot { mainNumbers.contains(it) }
            .random()

        // 3) 엔티티 생성 및 저장
        val entity = WinningNumbersEntity(
            mainNumbers = mainNumbers.joinToString(","),
            bonusNumber = bonus
        )

        winningNumbersRepository.save(entity)
    }
}
