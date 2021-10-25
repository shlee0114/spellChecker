package com.grammer.grammerchecker.model.dto.request

import com.grammer.grammerchecker.model.domain.SentenceLog
import com.grammer.grammerchecker.model.domain.WordLog
import lombok.Data
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.time.Clock
import java.time.LocalDateTime

@Data
data class LogRequest(
    val errorText: String,
    val fixedText: String,
    val fixedCount: Int = 0,
    val ip: String = ""
) {

    fun toSentenceLogConverter() = SentenceLog(
        Id = 0,
        errorSentence = errorText,
        fixedSentence = fixedText,
        fixedWordCount = fixedCount,
        ip = ip,
        fixedTime = LocalDateTime.now(Clock.systemUTC())
    )

    fun toWordLogConverter() = WordLog(
        Id = 0,
        errorWord = errorText,
        fixedWord = fixedText,
        ip = ip,
        fixedTime = LocalDateTime.now(Clock.systemUTC())
    )

    override fun toString() =
        ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("errorText", errorText)
            .append("fixedText", fixedText)
            .append("fixedCount", fixedCount)
            .append("ip", ip)
            .toString()
}