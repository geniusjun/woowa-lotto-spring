package com.geniusjun.lotto.application.lotto

import com.geniusjun.lotto.domain.lotto.*
import com.geniusjun.lotto.domain.member.MemberRepository
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
            .orElseThrow { IllegalArgumentException("존재하지 않는 회원입니다. id=$memberId") }

        // 구매 시 balance 차감
        member.decreaseBalance(LOTTO_PRICE)

        // 랜덤 로또 생성
        val myLotto = LottoNumberGenerator.generate()

        // 최신 당첨 번호 조회
        val latestWinning = winningNumbersService.getLatest()
            ?: throw IllegalStateException("등록된 당첨 번호가 없습니다.")
        val winningNumbers = WinningNumbers.of(
            mainNumbers = latestWinning.mainNumbers,
            bonus = latestWinning.bonusNumber
        )

        // 결과 계산
        val result = LottoMatcher.match(myLotto, winningNumbers)

        // 당첨금 지급
        if (result.reward > 0) {
            member.increaseBalance(result.reward.toLong())
        }

        // DB 반영
        memberRepository.save(member)

        // 응답 생성
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
