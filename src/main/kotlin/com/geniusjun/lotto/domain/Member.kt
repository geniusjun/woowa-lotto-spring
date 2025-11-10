package com.geniusjun.lotto.domain

import com.geniusjun.lotto.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "members")
class Member(

    @Column(nullable = false, unique = true)
    var nickname: String,

    @Column(nullable = false)
    var balance: Long = 0L

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
