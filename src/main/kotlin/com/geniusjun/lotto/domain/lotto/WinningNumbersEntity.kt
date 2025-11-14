package com.geniusjun.lotto.domain.lotto

import com.geniusjun.lotto.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "winning_numbers")
class WinningNumbersEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "main_numbers", columnDefinition = "jsonb", nullable = false)
    var mainNumbers: List<Int>,

    @Column(name = "bonus_number", nullable = false)
    var bonusNumber: Int

) : BaseEntity() {

    fun update(main: List<Int>, bonus: Int) {
        this.mainNumbers = main
        this.bonusNumber = bonus
    }
}
