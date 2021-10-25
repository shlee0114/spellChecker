package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.model.dto.LogDto
import com.grammer.grammerchecker.handlers.repository.SentenceLogRepository
import com.grammer.grammerchecker.model.request.LogRequest
import com.grammer.grammerchecker.utils.ApiUtils
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class LogHandler(private val repository: SentenceLogRepository) {

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
                    repository.save(log.toSentenceLogConverter())
                }
        )
}