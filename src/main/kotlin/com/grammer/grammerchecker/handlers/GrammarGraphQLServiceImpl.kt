package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.handlers.repository.WordLogRepository
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
class GrammarGraphQLServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: WordLogRepository
) {
    val sort = Sort.by(Sort.Direction.DESC, "fixed_time")

    fun checkGrammar(grammar: String) : Flux<GrammarDto> =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    fun findAll() =
        logRepository.findAll(sort)

    @Transactional
    fun logSave(log: GrammarWordLog) =
        logRepository.save(log)
}