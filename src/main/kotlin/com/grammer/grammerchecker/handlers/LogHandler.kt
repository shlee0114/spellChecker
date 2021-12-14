package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.model.dto.LogRequest
import com.grammer.grammerchecker.utils.ApiUtils
import com.grammer.grammerchecker.validator.impl.LogValidator
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


@Component
class LogHandler(
    private val validator: LogValidator,
    private val service: GrammarServiceImpl
) {

    fun logList(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .body(
            service.findAll()
                .collectList()
                .flatMap {
                    Mono.just(ApiUtils(response = it))
                }
        )

    fun insertLog(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .body(
            req.bodyToMono(LogRequest::class.java)
                .switchIfEmpty(Mono.empty())
                .flatMap { log ->
                    validator.validationCheck(log)
                    service.logSave(log)
                    Mono.just(ApiUtils(response = true))
                }
        )
}