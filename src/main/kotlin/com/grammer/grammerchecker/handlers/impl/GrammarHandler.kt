package com.grammer.grammerchecker.handlers.impl

import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.utils.GrammarChecker
import com.grammer.grammerchecker.validator.GrammarValidator
import com.grammer.grammerchecker.validator.LogValidator
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
    private val checker: GrammarChecker,
    private val validator: GrammarValidator
) {

    fun checkGrammar(req: ServerRequest): Mono<ServerResponse> = ok()
        .body(
            Flux.just(
                req.queryParam("grammar")
                    .orElse("")
            ).flatMap {
                validator.validate(it)
                checker.checkGrammar(it)
            }.collectList()
                .flatMap {
                    Mono.just(
                        ApiUtils(response = it)
                    )
                }
        )
}