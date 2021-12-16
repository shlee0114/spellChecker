package com.grammer.grammerchecker.handlers.validator

import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import com.grammer.grammerchecker.utils.ValidationText
import org.springframework.stereotype.Component

@Component
class LogValidator {
    fun validationCheck(target: LogRequest) {
        if(target.errorText.isBlank()) {
            error(
                ValidationText.invalidValue("errorText")
            )
        }
        if(target.fixedText.isBlank()) {
            error(
                ValidationText.invalidValue("fixedText")
            )
        }

        if (target.errorText.trim().length > 500) {
            error(
                ValidationText.sizeLess("errorText", 500)
            )
        }

        if (target.fixedText.trim().length > 500) {
            error(
                ValidationText.sizeLess("fixedText", 500)
            )
        }
    }
}