package com.grammer.grammerchecker.grammar_checker.repository

import com.grammer.grammerchecker.grammar_checker.domain.SentenceLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface SentenceLogRepository : ReactiveSortingRepository<SentenceLog, Long> {
}