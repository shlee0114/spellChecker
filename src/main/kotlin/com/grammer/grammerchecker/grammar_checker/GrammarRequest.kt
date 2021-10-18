package com.grammer.grammerchecker.grammar_checker

import lombok.Data
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Data
data class GrammarRequest(
    @get:NotBlank(message = "text must be provided")
    @get:Size(message = "text must be less than 500", max=500)
    val text: String
) {
    override fun toString() =
        ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("text", text)
            .toString()
}