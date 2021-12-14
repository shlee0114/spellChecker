package com.grammer.grammerchecker.handlers

import com.grammer.grammerchecker.handlers.repository.SentenceLogRepository
import com.grammer.grammerchecker.model.domain.GrammarSentenceLog
import com.grammer.grammerchecker.utils.GrammarChecker
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GrammarServiceImpl(
    private val grammarChecker: GrammarChecker,
    private val logRepository: SentenceLogRepository
) {
    val sort = Sort.by(Sort.Direction.DESC, "fixed_time")

    fun checkGrammar(grammar: String) =
        grammarChecker.checkGrammar(grammar)

    @Transactional(readOnly = true)
    fun findAll() =
        logRepository.findAll(sort)

    @Transactional
    fun logSave(log: GrammarSentenceLog) =
        logRepository.save(log)
}