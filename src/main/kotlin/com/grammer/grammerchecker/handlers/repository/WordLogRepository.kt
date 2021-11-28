package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.model.domain.GrammarWordLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface WordLogRepository : ReactiveSortingRepository<GrammarWordLog, Long> {
}