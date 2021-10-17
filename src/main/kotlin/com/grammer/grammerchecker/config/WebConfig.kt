package com.grammer.grammerchecker.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebMvc
@Configuration
class WebConfig : WebMvcConfigurer{
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedHeaders("*")
            .allowedOrigins("http://zifori.me:3000")
            .allowedMethods("*")
            .maxAge(1800)
            .allowCredentials(false)
    }
}