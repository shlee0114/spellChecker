package com.grammer.grammerchecker.validator.impl

import com.grammer.grammerchecker.validator.CommonValidator
import com.grammer.grammerchecker.validator.ValidationText
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.web.server.ResponseStatusException


@Component
class GrammarValidator : CommonValidator() {
    override fun supports(clazz: Class<*>) =
        String::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        val text = target as String

        if(text.isBlank()) {
            error(
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ValidationText.invalidValue("grammar")
                )
            )
        }
        if(text.length > 500) {
            error(
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ValidationText.sizeLess("grammar", 500)
                )
            )
        }

    }
}