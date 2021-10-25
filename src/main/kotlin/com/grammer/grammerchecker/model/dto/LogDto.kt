package com.grammer.grammerchecker.model.dto

import com.grammer.grammerchecker.model.domain.SentenceLog
import com.grammer.grammerchecker.model.domain.WordLog

data class LogDto(
    val error: String,
    val fixed: String,
    val count: Int,
    val fixedTime: String
) {
    constructor(source: SentenceLog) : this(
        source.errorSentence,
        source.fixedSentence,
        source.fixedWordCount,
        source.fixedTime.toString()
    )

    constructor(source: WordLog) : this(
        source.errorWord,
        source.fixedWord,
        0,
        source.fixedTime.toString()
    )
}