package com.grammer.grammerchecker.grammar_checker.domain

import org.springframework.data.jpa.repository.Temporal
import java.util.*
import javax.persistence.*

@Entity(name="grammar_word_log")
data class WordLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val Id: Long,
    val errorWord: String,
    val fixedWord: String,
    val ip: String,
    @Temporal(TemporalType.TIMESTAMP)
    val fixedTime: Date
)