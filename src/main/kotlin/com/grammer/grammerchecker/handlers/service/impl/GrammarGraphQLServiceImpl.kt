package com.grammer.grammerchecker.handlers.service.impl

import com.grammer.grammerchecker.handlers.repository.WordLogRepository
import com.grammer.grammerchecker.handlers.service.GrammarService
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
class GrammarGraphQLServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: WordLogRepository
) : GrammarService<GrammarWordLog>() {

    override fun checkGrammar(grammar: String) : Flux<GrammarDto> =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    override fun findAll() =
        logRepository.findAll(sort)

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun logSave(log: GrammarWordLog) =
        logRepository.save(log)
}