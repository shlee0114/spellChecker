package com.grammer.grammerchecker.routers

import com.grammer.grammerchecker.handlers.GrammarHandler
import com.grammer.grammerchecker.handlers.LogHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router


@Configuration
class Router(
    private val grammarHandler: GrammarHandler,
    private val logHandler: LogHandler
    ) {

    @Bean
    fun routerFunction() = nest(
        RequestPredicates.path("/api"),
        router {
            charset("UTF-8")
            listOf(
                GET("/check", grammarHandler::checkGrammar),
                GET("/log", logHandler::logList),
                POST("/log", logHandler::insertLog)
            )
        }
    )

}