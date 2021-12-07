package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SentenceLogRepository : ReactiveSortingRepository<GrammarSentenceLog, Long> {
}