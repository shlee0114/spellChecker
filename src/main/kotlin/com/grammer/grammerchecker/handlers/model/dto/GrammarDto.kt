package com.grammer.grammerchecker.handlers.model.dto

data class GrammarDto(
    var errorText: String,
    var fixedText: String
) {
    constructor() : this("", "")
}