package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.handlers.repository.WordLogRepository
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GrammarGraphQLServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: WordLogRepository
) : GrammarService {
    override val sort: Sort = Sort.by(Sort.Direction.DESC, "fixed_time")

    override fun checkGrammar(grammar: String) : Flux<GrammarDto> =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    override fun findAll() =
        logRepository.findAll(sort)
            .flatMap {
                Flux.just(LogDto(it))
            }

    @Transactional
    override fun logSave(log: LogRequest) =
        logRepository.save(GrammarWordLog(log))
            .flatMap {
                Mono.just(LogDto(it))
            }
}