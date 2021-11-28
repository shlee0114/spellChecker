package com.grammer.grammerchecker.graphql

import com.grammer.grammerchecker.handlers.service.impl.GrammarGraphQLServiceImpl
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.model.domain.GrammarWordLog
import com.grammer.grammerchecker.validator.impl.LogValidator
import graphql.kickstart.tools.GraphQLMutationResolver
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Controller
class GrammarCheckMutation(
    private val service: GrammarGraphQLServiceImpl,
    private val validator: LogValidator
) : GraphQLMutationResolver {

    suspend fun log(log: LogRequest): Boolean =
        Mono.just(log).flatMap {
            val errors = BeanPropertyBindingResult(it, LogRequest::class.java.name)
            validator.validate(it, errors)

            if (errors.allErrors.isEmpty()) {
                service.logSave(GrammarWordLog(it))
            } else {
                error(
                    ResponseStatusException(
                        HttpStatus.BAD_REQUEST, errors.allErrors.toString()
                    )
                )
            }
        }.thenReturn(true).awaitFirst()
}