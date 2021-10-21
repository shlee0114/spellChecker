package com.grammer.grammerchecker.grammar_checker.graphql

import com.expediagroup.graphql.spring.operations.Mutation
import com.grammer.grammerchecker.grammar_checker.LogRequest
import graphql.GraphQLException
import org.springframework.stereotype.Component
import javax.validation.Valid

@Component
class GrammarCheckMutation(val service: GrammarCheckService) : Mutation {

    fun log(@Valid log: LogRequest) : Boolean{
        try {
            service.insertWordLog(log)

            return true
        } catch (e: Exception) {
            throw GraphQLException(e.message)
        }
    }
}