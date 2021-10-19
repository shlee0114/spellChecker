package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarCheckService
import com.grammer.grammerchecker.grammar_checker.GrammarDto
import com.grammer.grammerchecker.grammar_checker.GrammarRequest
import graphql.GraphQLException
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Component
@Validated
class GrammarCheckResolver(val service: GrammarCheckService) : GraphQLQueryResolver {

    fun check(@Valid text: GrammarRequest) : GrammarDomain {
        try {
            val results = service.checkGrammar(text.text)

            return if (results.get().isEmpty()) {
                GrammarDomain()
            } else {
                GrammarDomain().apply { converter(results.get()[0]) }
            }
        } catch (e: IllegalArgumentException) {
            throw GraphQLException(e.message)
        }
    }

}