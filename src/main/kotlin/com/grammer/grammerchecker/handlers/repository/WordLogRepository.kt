package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.handlers.model.domain.GrammarWordLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface WordLogRepository : ReactiveSortingRepository<GrammarWordLog, Long> {
}