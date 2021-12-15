package com.grammer.grammerchecker.handlers.validator

import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import com.grammer.grammerchecker.utils.CommonValidator
import com.grammer.grammerchecker.utils.ValidationText
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils

@Component
class LogValidator : CommonValidator() {
    override fun supports(clazz: Class<*>) =
        LogRequest::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
            "errorText",
            ValidationText.invalidValue("errorText")
        )
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
            "fixedText",
            ValidationText.invalidValue("fixedText")
        )

        val request = target as LogRequest

        if (request.errorText.trim().length > 500) {
            errors.rejectValue(
                "errorText",
                ValidationText.sizeLess("errorText", 500)
            )
        }

        if (request.fixedText.trim().length > 500) {
            errors.rejectValue(
                "fixedText",
                ValidationText.sizeLess("fixedText", 500)
            )
        }
    }
}