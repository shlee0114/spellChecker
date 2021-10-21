package com.grammer.grammerchecker.grammar_checker.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("grammar_sentence_log")
data class SentenceLog (
    @Id
    val Id: Long,
    @Column( "error_sentence")
    val errorSentence: String,
    @Column( "fixed_sentence")
    val fixedSentence: String,
    @Column( "fixed_word_count")
    val fixedWordCount: Int,
    @Column( "ip")
    val ip: String,

    @Column( "fixed_time")
    val fixedTime: LocalDateTime = LocalDateTime.now()
)