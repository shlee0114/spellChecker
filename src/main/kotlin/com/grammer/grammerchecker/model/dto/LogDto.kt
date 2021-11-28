package com.grammer.grammerchecker.model.dto

import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import com.grammer.grammerchecker.model.domain.GrammarWordLog

data class LogDto(
    val error: String,
    val fixed: String,
    val count: Int,
    val fixedTime: String
) {
    constructor(source: GrammarSentenceLog) : this(
        source.errorSentence,
        source.fixedSentence,
        source.fixedWordCount,
        source.fixedTime.toString()
    )

    constructor(source: GrammarWordLog) : this(
        source.errorWord,
        source.fixedWord,
        0,
        source.fixedTime.toString()
    )
}