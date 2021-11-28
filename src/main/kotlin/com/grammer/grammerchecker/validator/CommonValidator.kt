package com.grammer.grammerchecker.validator

import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Validator

abstract class CommonValidator : Validator {
    fun validationCheck(any: Any) {
        val errors = BeanPropertyBindingResult(any, any::class.java.name)
        validate(any, errors)

        if(errors.allErrors.isNotEmpty()) {
            error(errors.allErrors[0].codes?.last().toString())
        }
    }
}