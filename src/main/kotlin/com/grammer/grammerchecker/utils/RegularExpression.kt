package com.grammer.grammerchecker.utils

class RegularExpression {
    fun getBetween(startString: String, endString: String, value: String) : String =
        Regex("(\\b${startString}\\b)(.*?)(\\b${endString}\\b)")
            .find(value)?.value?:""

    fun getInt(value: String) : Int =
        (Regex("[0-9]")
            .find(value)?.value?:"-1").toInt()
}