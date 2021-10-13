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
    fun check(@GraphQLArgument(name = "text") text : String) : GrammarDomain{
        try{
            if(text.split(" ").size > 1)
                throw IllegalArgumentException("no whitespace")

            val results = service.checkGrammar(text)

            return if(results.get().isEmpty()){
                GrammarDomain()
            }else{
                val result = GrammarDomain()
                result.converter(results.get()[0])
                result
            }
        }catch (e : IllegalArgumentException){
            throw IllegalArgumentException(e.message, e)
        }
    }

}