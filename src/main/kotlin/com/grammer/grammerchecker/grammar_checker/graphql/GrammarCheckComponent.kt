package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarCheckService
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Service

@Service
@GraphQLApi
class GrammarCheckComponent(val service: GrammarCheckService) {

    @GraphQLQuery(name="check")
    fun check(@GraphQLArgument(name = "text") text : String) : GrammarDomain?{
        val results = service.checkGrammar(text)

        return if(results.orNull() == null){
            null
        }else{
            val result = GrammarDomain()
            result.converter(results.get()[0])
            result
        }
    }

}