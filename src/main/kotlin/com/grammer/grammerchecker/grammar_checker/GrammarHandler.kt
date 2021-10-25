package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import java.util.*

@Component
class GrammarHandler(private val checker: GrammarChecker) {

    fun checkGrammar(req: ServerRequest): Mono<ServerResponse> = ok()
        .body(
            checker.checkGrammar(
                req.queryParam("grammar")
                    .filter(Objects::nonNull)
                    .orElse("")
            ).collectList()
                .flatMap {
                    Mono.just(
                        ApiUtils(response = it)
                    )
                }
        )
}