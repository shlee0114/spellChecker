package com.grammer.grammerchecker.grammar_checker.domain

import org.springframework.data.jpa.repository.Temporal
import java.util.*
import javax.persistence.*

@Entity(name="grammar_sentence_log")
data class SentenceLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val Id: Long,
    val errorSentence: String,
    val fixedSentence: String,
    val fixedWordCount: Int,
    val ip: String,

    @Temporal(TemporalType.TIMESTAMP)
    val fixedTime: Date
)