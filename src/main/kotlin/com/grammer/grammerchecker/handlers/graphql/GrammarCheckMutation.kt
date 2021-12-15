package com.grammer.grammerchecker.handlers.graphql

import com.grammer.grammerchecker.handlers.service.impl.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import com.grammer.grammerchecker.handlers.validator.LogValidator
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