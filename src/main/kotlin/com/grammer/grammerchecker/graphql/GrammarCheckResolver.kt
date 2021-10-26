package com.grammer.grammerchecker.graphql

import com.grammer.grammerchecker.model.dto.GrammarDto
import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.model.dto.request.LogRequest
import com.grammer.grammerchecker.handlers.repository.WordLogRepository
import com.grammer.grammerchecker.utils.GrammarChecker
import com.grammer.grammerchecker.validator.GrammarValidator
import graphql.kickstart.tools.GraphQLQueryResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GrammarCheckResolver(
    private val repository: WordLogRepository,
    private val checker: GrammarChecker,
    private val validator: GrammarValidator
) : GraphQLQueryResolver {

    suspend fun check(text: String): GrammarDto =
        Flux.just(text).flatMap {
            validator.validate(it)
            checker.checkGrammar(it)
        }.collectList()
            .flatMap {
                if (it.isEmpty()) {
                    Mono.empty<GrammarDto>()
                }
                Mono.just(it[0])
            }.awaitFirst()

    suspend fun log(): List<LogDto> =
        repository.findAll(
            Sort.by(Sort.Direction.DESC, "fixedTime")
        ).flatMap {
            Flux.just(LogDto(it))
        }.collectList()
            .awaitFirst()


    suspend fun log(log: LogRequest): Boolean =
        repository.save(log.toWordLogConverter())
            .thenReturn(true).awaitFirst()
}