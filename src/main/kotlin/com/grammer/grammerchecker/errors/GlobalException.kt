package com.grammer.grammerchecker.errors

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class GlobalException : ResponseStatusException {
    private val log = LoggerFactory.getLogger(javaClass)

    constructor(status: HttpStatus) : super(status)

    constructor(status: HttpStatus, reason: String) : super(status, reason)

    constructor(status: HttpStatus, reason: String, cause: Throwable) : super(status, reason, cause)

}