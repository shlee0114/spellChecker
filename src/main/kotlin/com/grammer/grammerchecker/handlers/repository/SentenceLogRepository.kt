package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface SentenceLogRepository : ReactiveSortingRepository<GrammarSentenceLog, Long> {
}