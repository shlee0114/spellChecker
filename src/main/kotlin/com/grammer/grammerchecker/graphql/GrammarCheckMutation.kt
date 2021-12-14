package com.grammer.grammerchecker.graphql

import com.grammer.grammerchecker.handlers.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.validator.impl.LogValidator
import graphql.kickstart.tools.GraphQLMutationResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GrammarCheckMutation(
    private val service: GrammarGraphQLServiceImpl,
    private val validator: LogValidator
) : GraphQLMutationResolver {

    suspend fun log(log: LogRequest): Boolean =
        Mono.just(log).flatMap {
            validator.validationCheck(it)
            service.logSave(it)
        }
            .thenReturn(true)
            .awaitFirst()
}