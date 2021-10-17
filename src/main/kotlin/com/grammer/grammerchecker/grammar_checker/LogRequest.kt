package com.grammer.grammerchecker.grammar_checker

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class LogRequest(
    @get:NotBlank(message = "errorText must be provided")
    @get:Size(message = "errorText must be less than 500", max=500)
    @get:NotEmpty(message = "errorText must be provided")
    val errorText: String,

    @get:NotBlank(message = "fixedText must be provided")
    @get:Size(message = "fixedText must be less than 500", max=500)
    @get:NotEmpty(message = "fixedText must be provided")
    val fixedText: String,
    val fixedCount: Int = 0,
    val ip: String = ""
) {
    override fun toString() =
        ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("errorText", errorText)
            .append("fixedText", fixedText)
            .append("fixedCount", fixedCount)
            .append("ip", ip)
            .toString()
}