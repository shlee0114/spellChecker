package com.grammer.grammerchecker.model.domain

import com.grammer.grammerchecker.model.dto.LogRequest
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Clock
import java.time.LocalDateTime
import javax.annotation.Generated

@Table("grammar_sentence_log")
data class GrammarSentenceLog(
    @Id
    @Generated
    val Id: Long,
    val errorSentence: String,
    val fixedSentence: String,
    val fixedWordCount: Int,
    val ip: String,
    val fixedTime: LocalDateTime
) {
    constructor(log: LogRequest) : this(
        Id = 0,
        errorSentence = log.errorText,
        fixedSentence = log.fixedText,
        fixedWordCount = log.fixedCount,
        ip = log.ip,
        fixedTime = LocalDateTime.now(Clock.systemUTC())
    )
}