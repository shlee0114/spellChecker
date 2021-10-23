package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import java.util.*

@Component
class GrammarHandler(
    private val repository: SentenceLogRepository,
    private val checker: GrammarChecker
    ) {

    fun checkGrammar(req: ServerRequest): Mono<ServerResponse> = ok()
        .body<Flux<GrammarDto>>(
            checker.checkGrammar(
                req.queryParam("grammar")
                    .filter(Objects::nonNull)
                    .orElse("")
            )
        )

    fun logList(req: ServerRequest): Mono<ServerResponse> = ok()
        .body(
            repository.findAll(
                Sort.by(Sort.Direction.DESC, "fixedTime")
            ).flatMap { Flux.just(LogDto(it)) }
        )

    fun insertLog(req: ServerRequest): Mono<ServerResponse> = ok()
        .body(
            req.bodyToMono(LogRequest::class.java)
                .switchIfEmpty(Mono.empty())
                .flatMap { log ->
                        repository.save(log.toSentenceLogConverter())
                }
        )
}