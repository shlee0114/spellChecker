package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarCheckService
import com.grammer.grammerchecker.grammar_checker.GrammarDto
import com.grammer.grammerchecker.grammar_checker.GrammarRequest
import com.grammer.grammerchecker.grammar_checker.LogDto
import graphql.GraphQLException
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class GrammarCheckResolver(val service: GrammarCheckService) : GraphQLQueryResolver {

    fun check(@Valid text: GrammarRequest) : GrammarDto {
        try {
            val results = service.checkGrammar(text.text)

            return if (results.get().isEmpty()) {
                GrammarDto("", "")
            } else {
                results.get()[0]
            }
        } catch (e: IllegalArgumentException) {
            throw GraphQLException(e.message)
        }
    }

    fun log() : List<LogDto> {
        try {
            return service.wordLogList().map { LogDto(it) }
        } catch (e: IllegalArgumentException) {
            throw GraphQLException(e.message)
        }
    }
}