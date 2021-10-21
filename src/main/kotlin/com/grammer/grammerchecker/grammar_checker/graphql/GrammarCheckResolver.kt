package com.grammer.grammerchecker.grammar_checker.graphql

import com.expediagroup.graphql.spring.operations.Query
import com.grammer.grammerchecker.grammar_checker.GrammarCheckService
import com.grammer.grammerchecker.grammar_checker.GrammarDto
import com.grammer.grammerchecker.grammar_checker.GrammarRequest
import com.grammer.grammerchecker.grammar_checker.LogDto
import graphql.GraphQLException
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@Component
class GrammarCheckResolver(val service: GrammarCheckService) : Query {

    fun check(@Valid text: GrammarRequest) : Mono<GrammarDto> {
        try {
            val results = service.checkGrammar(text.text)

            return results.next()
        } catch (e: IllegalArgumentException) {
            throw GraphQLException(e.message)
        }
    }

    fun log() : Flux<LogDto> {
        try {
            return service.wordLogList().map { LogDto(it) }
        } catch (e: IllegalArgumentException) {
            throw GraphQLException(e.message)
        }
    }
}