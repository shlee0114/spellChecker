package com.grammer.grammerchecker.graphql

import com.grammer.grammerchecker.handlers.service.impl.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.validator.impl.GrammarValidator
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
            validator.validate(it)
            service.checkGrammar(it)
        }.collectList()
            .flatMap {
                if (it.isEmpty()) {
                    Mono.just(GrammarDto())
                } else {
                    Mono.just(it[0])
                }
            }.awaitFirst()

    suspend fun log(): List<LogDto> =
        service.findAll()
            .flatMap {
                Flux.just(LogDto(it))
            }.collectList()
            .awaitFirst()

    suspend fun log(log: LogRequest): Boolean =
        service.logSave(GrammarWordLog(log))
            .thenReturn(true).awaitFirst()
}