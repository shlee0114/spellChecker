package com.grammer.grammerchecker.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer


@EnableWebFlux
@Configuration
class WebConfig : WebFluxConfigurer{
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedHeaders("*")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("*")
            .maxAge(1800)
            .allowCredentials(false)
    }
}