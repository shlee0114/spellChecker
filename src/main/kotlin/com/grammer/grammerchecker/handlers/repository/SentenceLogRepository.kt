package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.model.domain.SentenceLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface SentenceLogRepository : ReactiveSortingRepository<SentenceLog, Long> {
}