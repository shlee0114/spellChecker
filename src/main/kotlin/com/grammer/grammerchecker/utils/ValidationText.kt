package com.grammer.grammerchecker.utils

class ValidationText {
    companion object {
        fun sizeLess(valueName: String, length: Int) =
            "'$valueName' must be less than $length"

        fun invalidValue(valueName: String) =
            "invalid value '$valueName'"
    }
}