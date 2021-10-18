package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarCheckService
import com.grammer.grammerchecker.grammar_checker.LogRequest
import graphql.GraphQLException
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component
import javax.transaction.Transactional
import javax.validation.Valid

@Component
class GrammarCheckMutation(val service: GrammarCheckService) : GraphQLMutationResolver {

    @Transactional
    fun log(@Valid log: LogRequest) : Boolean{
        try {
            service.insertWordLog(log)

            return true
        } catch (e: Exception) {
            throw GraphQLException(e.message)
        }
    }
}