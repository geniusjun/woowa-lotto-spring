package com.geniusjun.lotto.domain.member

import com.geniusjun.lotto.domain.common.BaseEntity
import com.geniusjun.lotto.domain.member.exception.InvalidBalanceException
import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(

    @Column(nullable = false, unique = true)
    var nickname: String,

    @Column(nullable = false)
    var balance: Long = 0L,

    @Column(nullable = true, unique = true)
    var googleSub: String? = null

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun decreaseBalance(amount: Long) {
        if (amount <= 0) {
            throw InvalidBalanceException("차감 금액은 0보다 커야 합니다. (입력값: $amount)")
        }
        if (balance < amount) {
            throw InvalidBalanceException("잔액이 부족합니다. (현재 잔액: $balance, 필요 금액: $amount)")
        }
        balance -= amount
    }

    fun increaseBalance(amount: Long) {
        if (amount < 0) {
            throw InvalidBalanceException("보상 금액은 음수가 될 수 없습니다. (입력값: $amount)")
        }
        balance += amount
    }
}
