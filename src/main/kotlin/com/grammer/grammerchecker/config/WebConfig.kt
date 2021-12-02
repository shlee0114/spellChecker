package com.grammer.grammerchecker.config

import com.grammer.grammerchecker.handlers.GrammarHandler
import com.grammer.grammerchecker.handlers.LogHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.router


@EnableWebFlux
@Configuration
class WebConfig(
    private val grammarHandler: GrammarHandler,
    private val logHandler: LogHandler
    ) : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedHeaders("*")
            .allowedOrigins("http://zifori.me:3000")
            .allowedMethods("*")
            .maxAge(1800)
            .allowCredentials(false)
    }

    @Bean
    fun routerFunction() = RouterFunctions.nest(
        RequestPredicates.path("/api"),
        router {
            contentType(MediaType.APPLICATION_JSON)
            charset("UTF-8")
            listOf(
                GET("/check", grammarHandler::checkGrammar),
                GET("/log", logHandler::logList),
                POST("/log", logHandler::insertLog)
            )
        }
    )
}