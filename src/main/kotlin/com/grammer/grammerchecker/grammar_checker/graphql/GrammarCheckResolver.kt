package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.dto.GrammarDto
import com.grammer.grammerchecker.grammar_checker.dto.LogDto
import com.grammer.grammerchecker.grammar_checker.request.LogRequest
import com.grammer.grammerchecker.grammar_checker.repository.WordLogRepository
import com.grammer.grammerchecker.utils.GrammarChecker
import graphql.kickstart.tools.GraphQLQueryResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GrammarCheckResolver(
    private val repository: WordLogRepository,
    private val checker: GrammarChecker
) : GraphQLQueryResolver {

    suspend fun check(text: String): GrammarDto =
        checker.checkGrammar(text)
            .collectList()
            .flatMap {
            if(it.isEmpty()){
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