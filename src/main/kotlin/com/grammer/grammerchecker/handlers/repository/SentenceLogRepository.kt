package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.handlers.model.domain.GrammarSentenceLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface SentenceLogRepository : ReactiveSortingRepository<GrammarSentenceLog, Long>