package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.*
import com.geniusjun.lotto.domain.lotto.exception.WinningNumbersNotFoundException
import com.geniusjun.lotto.domain.member.MemberRepository
import com.geniusjun.lotto.domain.member.exception.MemberNotFoundException
import com.geniusjun.lotto.presentation.lotto.dto.LottoDrawResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LottoDrawService(
    private val memberRepository: MemberRepository,
    private val winningNumbersService: WinningNumbersService
) {

    companion object {
        private const val LOTTO_PRICE = 1000L
    }

    @Transactional
    fun drawForMember(memberId: Long): LottoDrawResponse {
        val member = memberRepository.findById(memberId)
            .orElseThrow { MemberNotFoundException("존재하지 않는 회원입니다. (id=$memberId)") }

        member.decreaseBalance(LOTTO_PRICE)

        val myLotto = LottoNumberGenerator.generate()

        val latestWinning = winningNumbersService.getLatest()
            ?: throw WinningNumbersNotFoundException("등록된 당첨 번호가 없습니다.")
        val winningNumbers = WinningNumbers.of(
            mainNumbers = latestWinning.mainNumbers,
            bonus = latestWinning.bonusNumber
        )

        val result = LottoMatcher.match(myLotto, winningNumbers)

        if (result.reward > 0) {
            member.increaseBalance(result.reward.toLong())
        }

        memberRepository.save(member)

        return buildResponse(myLotto, winningNumbers, result, member.balance)
    }

    private fun buildResponse(
        myLotto: LottoNumbers,
        winning: WinningNumbers,
        result: LottoResult,
        balance: Long
    ): LottoDrawResponse {
        val myNumbers = myLotto.toIntList()
        val winningNumbers = winning.numbers.toIntList()
        val matchedNumbers = myNumbers.filter { it in winningNumbers }

        return LottoDrawResponse.from(
            myNumbers = myNumbers,
            winningNumbers = winningNumbers,
            matchedNumbers = matchedNumbers,
            bonusNumber = winning.bonusNumber?.value,
            bonusMatched = result.bonusMatched,
            rank = result.rank.name,
            reward = result.reward,
            balance = balance
        )
    }
}
