package com.grammer.grammerchecker.handlers.repository

import com.grammer.grammerchecker.model.domain.WordLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface WordLogRepository : ReactiveSortingRepository<WordLog, Long> {
}