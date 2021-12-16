package com.grammer.grammerchecker.handlers.service

import com.grammer.grammerchecker.handlers.model.dto.GrammarDto
import com.grammer.grammerchecker.handlers.model.dto.LogDto
import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GrammarService {
    val sort : Sort

    fun checkGrammar(grammar: String) : Flux<GrammarDto>
    fun findAll() : Flux<LogDto>
    fun logSave(log: LogRequest) : Mono<LogDto>
}