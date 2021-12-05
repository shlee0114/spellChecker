package com.grammer.grammerchecker.graphql

import com.grammer.grammerchecker.handlers.service.impl.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.validator.impl.LogValidator
import graphql.kickstart.tools.GraphQLMutationResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class GrammarCheckMutation(
    private val service: GrammarGraphQLServiceImpl,
    private val validator: LogValidator
) : GraphQLMutationResolver {

    suspend fun log(log: LogRequest): Boolean =
        Mono.just(log).flatMap {
            validator.validationCheck(it)
            service.logSave(GrammarWordLog(it))
        }.thenReturn(true).awaitFirst()
}