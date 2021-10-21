package com.grammer.grammerchecker.grammar_checker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter


@Configuration(proxyBeanMethods = false)
class GrammarRouter() {

    @Bean
    fun route() = coRouter {
        "/api".nest{
            accept(MediaType.APPLICATION_JSON).nest{
                GET("/check", handler::checkAllSpell)
                GET("/log", handler::logList)
                POST("/log", handler::insertLog)
            }
        }
    }

}