package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.grammar_checker.domain.SentenceLog
import org.springframework.data.jpa.repository.JpaRepository

interface SentenceLogRepository : JpaRepository<SentenceLog, Long> {
}