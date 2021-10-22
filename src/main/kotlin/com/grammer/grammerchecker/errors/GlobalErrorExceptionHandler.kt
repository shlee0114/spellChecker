package com.grammer.grammerchecker.errors

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Component
class GlobalErrorExceptionHandler(
    error: ErrorAttributes,
    resource: WebProperties.Resources,
    context: ApplicationContext,
    configurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(error, resource, context) {

    init {
        this.setMessageWriters(configurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes) =
        RouterFunctions.route(
            RequestPredicates.all(),
            this::renderErrorResponse
        )

    private fun renderErrorResponse(request: ServerRequest) : Mono<ServerResponse> {
        val errorPropertiesMap = getErrorAttributes(
            request,
            ErrorAttributeOptions.defaults()
        )

         return ServerResponse.status(errorPropertiesMap["status"] as Int)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(errorPropertiesMap))
    }
}