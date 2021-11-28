package com.grammer.grammerchecker.validator.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException


@Component
class GrammarValidator {
    fun validate(text: String) {
        if(text.isBlank()) {
            error(
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST
                    , "text is empty"
                )
            )
        }
        if(text.length > 500) {
            error(
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST
                    , "text must be less than 500"
                )
            )
        }
    }
}