package com.grammer.grammerchecker.grammar_checker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router


@Configuration
class GrammarRouter(private val handler: GrammarHandler) {
    @Bean
    fun routerFunction() = nest(
        RequestPredicates.path("/api"),
        router {
            charset("UTF-8")
            listOf(
                GET("/check", handler::checkGrammar),
                GET("/log", handler::logList),
                POST("/log", handler::insertLog)
            )
        }
    )

}