package com.grammer.grammerchecker.utils

import com.grammer.grammerchecker.grammar_checker.dto.GrammarDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Component
class GrammarChecker {

    @Value("\${naver.url}")
    private lateinit var naverUrl: String

    @Value("\${naver.user-gent}")
    private lateinit var naverUserGent: String

    @Value("\${naver.referer}")
    private lateinit var naverReferer: String

    fun checkGrammar(grammar: String = "") =
        WebClient.create("$naverUrl$grammar")
            .get().headers {
                it.set("user-gent", naverUserGent)
                it.set("referer", naverReferer)
            }.retrieve()
            .bodyToFlux(String::class.java)
            .flatMap {
                val jsonOpen = it.indexOf("{")
                val jsonClose = it.lastIndex - 1
                val json = it.substring(jsonOpen, jsonClose)
                val expression = RegularExpression()

                val errorCount = expression.getInt(
                    expression.getBetween("errata_count", "origin_html", json)
                )
                val errorTextArray = expression.getBetween("origin_html", "html", json).split("</span>")
                val fixedTextArray = expression.getBetween("html", "notag_html", json).split("</span>")

                Flux.fromIterable(
                    MutableList(errorCount) { cnt ->
                        GrammarDto(
                            errorTextArray[cnt].substring(errorTextArray[cnt].indexOf(">") + 1),
                            fixedTextArray[cnt].substring(fixedTextArray[cnt].indexOf(">") + 1)
                        )
                    }
                )
            }

}