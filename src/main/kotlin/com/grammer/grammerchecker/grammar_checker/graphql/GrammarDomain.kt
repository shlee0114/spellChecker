package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarDto
import lombok.Value

@Value
class GrammarDomain {
    private var errorText: String = ""
    private var fixedText: String = ""

    fun converter(dto: GrammarDto) {
        this.errorText = dto.errorText
        this.fixedText = dto.fixedText
    }
}