package com.grammer.grammerchecker.handlers.impl

import com.grammer.grammerchecker.handlers.repository.SentenceLogRepository
import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.model.dto.request.LogRequest
import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.validator.LogValidator
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class LogHandler(
    private val repository: SentenceLogRepository,
    private val validator: LogValidator
) {

    fun logList(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .body(
            repository.findAll(
                Sort.by(Sort.Direction.DESC, "fixedTime")
            ).flatMap {
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
                        repository.save(log.toSentenceLogConverter())
                    } else {
                        error(
                            ResponseStatusException(
                                HttpStatus.BAD_REQUEST
                                , errors.allErrors.toString()
                            )
                        )
                    }
                }
        )
}