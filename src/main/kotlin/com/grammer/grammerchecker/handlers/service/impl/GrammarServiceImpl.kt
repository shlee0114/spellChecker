package com.grammer.grammerchecker.handlers.service.impl

import com.grammer.grammerchecker.handlers.repository.SentenceLogRepository
import com.grammer.grammerchecker.handlers.service.GrammarService
import com.grammer.grammerchecker.handlers.model.domain.GrammarSentenceLog
import com.grammer.grammerchecker.handlers.model.dto.LogDto
import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GrammarServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: SentenceLogRepository
) : GrammarService {
    override val sort: Sort = Sort.by(Sort.Direction.DESC, "fixed_time")

    override fun checkGrammar(grammar: String) =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    override fun findAll() =
        logRepository.findAll(sort)
            .flatMap {
                Flux.just(LogDto(it))
            }

    @Transactional
    override fun logSave(log: LogRequest) =
        logRepository.save(GrammarSentenceLog(log))
            .flatMap {
                Mono.just(LogDto(it))
            }
}