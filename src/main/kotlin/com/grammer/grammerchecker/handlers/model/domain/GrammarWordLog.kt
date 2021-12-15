package com.grammer.grammerchecker.handlers.model.domain

import com.grammer.grammerchecker.handlers.model.dto.LogRequest
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Clock
import java.time.LocalDateTime
import javax.annotation.Generated

@Table("grammar_word_log")
data class GrammarWordLog(
    @Id
    @Generated
    val Id: Long,
    val errorWord: String,
    val fixedWord: String,
    val ip: String,
    val fixedTime: LocalDateTime
) {
    constructor(log: LogRequest) : this(
        Id = 0,
        errorWord = log.errorText,
        fixedWord = log.fixedText,
        ip = log.ip,
        fixedTime = LocalDateTime.now(Clock.systemUTC())
    )
}