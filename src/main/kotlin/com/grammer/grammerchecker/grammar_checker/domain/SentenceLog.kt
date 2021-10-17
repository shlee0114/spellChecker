package com.grammer.grammerchecker.grammar_checker.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="grammar_sentence_log")
data class SentenceLog (
    @Id
    val Id: Long,
    val errorSentence: String,
    val fixedSentence: String,
    val fixedTime: String,
    val fixedWordCount: Int,
    val ip: String
)