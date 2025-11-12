package com.geniusjun.lotto.domain.lotto

import com.geniusjun.lotto.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "winning_numbers")
class WinningNumbersEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "round", nullable = false, unique = true)
    val round: Long,

    @ElementCollection
    @CollectionTable(
        name = "winning_main_numbers",
        joinColumns = [JoinColumn(name = "winning_id")]
    )
    @Column(name = "number")
    val mainNumbers: List<Int>,

    @Column(name = "bonus_number")
    val bonusNumber: Int? = null,
) : BaseEntity()

