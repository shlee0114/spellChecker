package com.grammer.grammerchecker.validator

import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Validator

abstract class CommonValidator : Validator {
    fun validationCheck(any: Any, name: String) {
        val errors = BeanPropertyBindingResult(any, name)
        validate(any, errors)

        if(errors.allErrors.isNotEmpty()) {
            error(errors.allErrors[0].codes?.last().toString())
        }
    }
}