package com.geniusjun.lotto.domain.member

import com.geniusjun.lotto.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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