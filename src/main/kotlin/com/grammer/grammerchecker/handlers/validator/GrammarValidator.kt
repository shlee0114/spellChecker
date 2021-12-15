package com.grammer.grammerchecker.handlers.validator

import com.grammer.grammerchecker.utils.CommonValidator
import com.grammer.grammerchecker.utils.ValidationText
import org.springframework.stereotype.Component
import org.springframework.validation.Errors


@Component
class GrammarValidator : CommonValidator() {
    override fun supports(clazz: Class<*>) =
        String::class.java.isAssignableFrom(clazz)

    override fun validate(target: Any, errors: Errors) {
        val text = target as String

        if (text.isBlank()) {
            error(
                ValidationText.invalidValue("grammar")
            )
        }
        if (text.length > 500) {
            error(
                ValidationText.sizeLess("grammar", 500)
            )
        }

    }
}