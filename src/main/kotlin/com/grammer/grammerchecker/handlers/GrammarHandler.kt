package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.handlers.service.impl.GrammarServiceImpl
import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.handlers.validator.GrammarValidator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux

@Component
class GrammarHandler(
    private val validator: GrammarValidator,
    private val service: GrammarServiceImpl
) {

    fun checkGrammar(req: ServerRequest): Mono<ServerResponse> = ok()
        .body(
            Flux.just(
                req.queryParam("grammar")
                    .orElse("")
            ).flatMap {
                validator.validationCheck(it)
                service.checkGrammar(it)
            }.collectList()
                .flatMap {
                    Mono.just(
                        ApiUtils(response = it)
                    )
                }
        )
}