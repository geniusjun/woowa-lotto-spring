package com.geniusjun.lotto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class LottoApplication

fun main(args: Array<String>) {
	runApplication<LottoApplication>(*args)
}
