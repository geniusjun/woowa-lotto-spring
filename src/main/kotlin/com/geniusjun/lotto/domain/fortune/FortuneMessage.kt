package com.geniusjun.lotto.domain.fortune

import com.geniusjun.lotto.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "fortune_messages")
class FortuneMessage(

    @Column(nullable = false)
    val message: String

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
