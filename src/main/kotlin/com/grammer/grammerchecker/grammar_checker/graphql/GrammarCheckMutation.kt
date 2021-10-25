package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.request.LogRequest
import com.grammer.grammerchecker.grammar_checker.repository.WordLogRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Controller

@Controller
class GrammarCheckMutation(private val repository: WordLogRepository) : GraphQLMutationResolver {


    suspend fun log(log: LogRequest): Boolean =
        repository.save(log.toWordLogConverter())
            .thenReturn(true).awaitFirst()
}