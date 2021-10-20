package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.grammar_checker.domain.WordLog
import org.springframework.data.repository.reactive.ReactiveSortingRepository

interface WordLogRepository : ReactiveSortingRepository<WordLog, Long> {
}