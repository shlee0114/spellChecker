package com.grammer.grammerchecker.handlers.service.impl

import com.grammer.grammerchecker.handlers.repository.SentenceLogRepository
import com.grammer.grammerchecker.handlers.service.GrammarService
import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
class GrammarServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: SentenceLogRepository
) : GrammarService<GrammarSentenceLog>() {

    override fun checkGrammar(grammar: String) : Flux<GrammarDto> =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    override fun findAll() =
        logRepository.findAll(sort)

    @Transactional(isolation = Isolation.READ_COMMITTED)
    override fun logSave(log: GrammarSentenceLog) =
        logRepository.save(log)
}