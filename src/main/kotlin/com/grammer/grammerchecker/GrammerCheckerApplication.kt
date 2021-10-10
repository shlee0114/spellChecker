package com.grammer.grammerchecker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrammerCheckerApplication

fun main(args: Array<String>) {
    runApplication<GrammerCheckerApplication>(*args)
}
