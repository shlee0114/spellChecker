package com.grammer.grammerchecker.grammar_checker.graphql

import com.grammer.grammerchecker.grammar_checker.GrammarDto
import io.leangen.graphql.annotations.GraphQLQuery

class GrammarDomain {
    private var errorText: String? = null
    private var fixedText: String? = null

    @GraphQLQuery(name = "errorText")
    fun getErrorText() = errorText

    @GraphQLQuery(name = "fixedText")
    fun getFixedText() = fixedText

    fun converter(dto: GrammarDto) {
        this.errorText = dto.errorText
        this.fixedText = dto.fixedText
    }
}