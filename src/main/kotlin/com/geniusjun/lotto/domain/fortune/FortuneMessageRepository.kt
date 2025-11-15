package com.geniusjun.lotto.domain.fortune

import org.springframework.data.jpa.repository.JpaRepository

interface FortuneMessageRepository : JpaRepository<FortuneMessage, Long>
