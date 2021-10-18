package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.grammar_checker.domain.WordLog
import org.springframework.data.jpa.repository.JpaRepository

interface WordLogRepository : JpaRepository<WordLog, Long> {
}