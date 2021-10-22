package com.grammer.grammerchecker.grammar_checker.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("grammar_word_log")
data class WordLog (
    @Id
    val Id: Long,
    @Column("error_word")
    val errorWord: String,
    @Column( "fixed_word")
    val fixedWord: String,
    @Column( "ip")
    val ip: String,

    @Column( "fixed_time")
    val fixedTime: LocalDateTime
)