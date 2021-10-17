package com.grammer.grammerchecker.grammar_checker.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="grammar_word_log")
data class WordLog (
    @Id
    val Id: String,
    val errorWord: String,
    val fixedWord: String,
    val fixedTime: String,
    val ip: String
)