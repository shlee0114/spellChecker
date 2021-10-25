package com.grammer.grammerchecker.validator

import com.grammer.grammerchecker.model.dto.request.LogRequest
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class LogValidator : Validator {
    override fun supports(clazz: Class<*>) =
        LogRequest::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "errorText", "field.required"
        )
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors, "fixedText", "field.required"
        )

        val request = target as LogRequest

        if (request.errorText.trim().length > 500) {
            errors.rejectValue(
                "errorText",
                "errorText must be less than 500")
        }

        if (request.fixedText.trim().length > 500) {
            errors.rejectValue(
                "fixedText",
                "fixedText must be less than 500")
        }
    }
}