package com.grammer.grammerchecker.model.dto

data class GrammarDto(
    var errorText: String,
    var fixedText: String
) {
    constructor() : this("", "")
}