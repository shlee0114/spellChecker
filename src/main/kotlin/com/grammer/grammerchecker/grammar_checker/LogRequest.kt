package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.grammar_checker.domain.SentenceLog
import com.grammer.grammerchecker.grammar_checker.domain.WordLog
import lombok.Data
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Data
data class LogRequest(
    @get:NotBlank(message = "errorText must be provided")
    @get:Size(message = "errorText must be less than 500", max=500)
    val errorText: String,

    @get:NotBlank(message = "fixedText must be provided")
    @get:Size(message = "fixedText must be less than 500", max=500)
    val fixedText: String,
    val fixedCount: Int = 0,
    val ip: String = ""
) {

    fun toSentenceLogConverter() = SentenceLog(
        Id=0,
        errorSentence = errorText,
        fixedSentence = fixedText,
        fixedWordCount = fixedCount,
        ip = ip,
        fixedTime = Date()
    )

    fun toWordLogConverter() = WordLog(
        Id=0,
        errorWord = errorText,
        fixedWord = fixedText,
        ip = ip,
        fixedTime = Date()
    )

    override fun toString() =
        ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("errorText", errorText)
            .append("fixedText", fixedText)
            .append("fixedCount", fixedCount)
            .append("ip", ip)
            .toString()
}