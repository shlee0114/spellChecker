package com.grammer.grammerchecker.handlers.service

import com.grammer.grammerchecker.model.dto.GrammarDto
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class GrammarService<T> {
    val sort = Sort.by(Sort.Direction.DESC, "fixed_time")

    abstract fun checkGrammar(grammar: String = "") : Flux<GrammarDto>

    abstract fun findAll() : Flux<T>

    abstract fun logSave(log: T) : Mono<T>
}