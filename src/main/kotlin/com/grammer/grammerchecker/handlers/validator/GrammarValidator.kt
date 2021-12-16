package com.grammer.grammerchecker.handlers.validator

import com.grammer.grammerchecker.utils.ValidationText
import org.springframework.stereotype.Component

@Component
class GrammarValidator {
    fun validationCheck(target: String) {
        if (target.isBlank()) {
            error(
                ValidationText.invalidValue("grammar")
            )
        }
        if (target.length > 500) {
            error(
                ValidationText.sizeLess("grammar", 500)
            )
        }
    }
}