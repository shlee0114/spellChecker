package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.grammar_checker.domain.SentenceLog
import com.grammer.grammerchecker.grammar_checker.domain.WordLog
import java.util.*

data class LogDto(
    val error : String,
    val fixed : String,
    val count : Int,
    val fixedTime: Date
) {
    constructor(source: SentenceLog) : this(
        source.errorSentence,
        source.fixedSentence,
        source.fixedWordCount,
        source.fixedTime
    )

    constructor(source: WordLog) : this(
        source.errorWord,
        source.fixedWord,
        0,
        source.fixedTime
    )
}