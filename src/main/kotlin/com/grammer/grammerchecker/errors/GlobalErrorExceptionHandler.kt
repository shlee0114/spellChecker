package com.grammer.grammerchecker.errors

import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.utils.ErrorUtils
import io.netty.handler.codec.http.HttpResponseStatus
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
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

    private fun renderErrorResponse(request: ServerRequest) =
        ServerResponse.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                Mono.just(
                    ApiUtils<Any>(
                        success = false, error = ErrorUtils(
                            message = getError(request).message ?: "",
                            status = HttpStatus.BAD_REQUEST.value()
                        )
                    )
                )
            )
}