package com.grammer.grammerchecker.handlers.graphql

import com.grammer.grammerchecker.handlers.service.impl.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.handlers.model.dto.GrammarDto
import com.grammer.grammerchecker.handlers.model.dto.LogDto
import com.grammer.grammerchecker.handlers.validator.GrammarValidator
import graphql.kickstart.tools.GraphQLQueryResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GrammarCheckResolver(
    private val service: GrammarGraphQLServiceImpl,
    private val validator: GrammarValidator
) : GraphQLQueryResolver {

    suspend fun check(text: String): GrammarDto =
        Flux.just(text).flatMap {
            validator.validationCheck(it)
            service.checkGrammar(it)
        }.collectList()
            .flatMap {
                if (it.isEmpty()) {
                    Mono.just(GrammarDto())
                } else {
                    Mono.just(it[0])
                }
            }.awaitFirst()

    suspend fun log(ver: String): List<LogDto> =
        service.findAll()
            .collectList()
            .awaitFirst()
}