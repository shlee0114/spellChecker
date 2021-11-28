package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.handlers.service.impl.GrammarServiceImpl
import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.validator.impl.LogValidator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class LogHandler(
    private val validator: LogValidator,
    private val service: GrammarServiceImpl
) {

    fun logList(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .body(
            service.findAll()
                .flatMap {
                    Flux.just(LogDto(it))
                }.collectList()
                .flatMap {
                    Mono.just(
                        ApiUtils(response = it)
                    )
                }
        )

    fun insertLog(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .body(
            req.bodyToMono(LogRequest::class.java)
                .switchIfEmpty(Mono.empty())
                .flatMap { log ->
                    val errors = BeanPropertyBindingResult(log, LogRequest::class.java.name)
                    validator.validate(log, errors)

                    if (errors.allErrors.isEmpty()) {
                        service.logSave(GrammarSentenceLog(log))
                    } else {
                        error(
                            ResponseStatusException(
                                HttpStatus.BAD_REQUEST, errors.allErrors.toString()
                            )
                        )
                    }
                }.then(
                    Mono.just(
                        ApiUtils(response = true)
                    )
                )
        )
}