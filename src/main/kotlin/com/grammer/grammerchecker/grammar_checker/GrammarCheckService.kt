package com.grammer.grammerchecker.grammar_checker

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.com.google.common.base.Optional
import graphql.com.google.common.base.Preconditions
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class GrammarCheckService {
    @Transactional(readOnly = true)
    fun checkGrammar(grammar : String) : Optional<Array<GrammarDto>>{

        if(grammar == "")
            throw IllegalArgumentException("grammar is required")
        if(grammar.length > 500){
            throw  IllegalArgumentException("grammar length must be less than 500 characters")
        }

        val header = HttpHeaders()
        header.set("user-gent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36                                         (KHTML, like Gecko)     Chrome/57.0.2987.133 Safari/537.36")
        header.set("referer", "https://search.naver.com/")

        val entity = HttpEntity<String>("", header)

        val restTemplate = RestTemplate()
        val respEntity = restTemplate.exchange("https://m.search.naver.com/p/csearch/ocontent/spellchecker.nhn?_callback=window.__jindo2_callback._spellingCheck_0&q=$grammar", HttpMethod.GET, entity, String::class.java)

        val objectMapper = ObjectMapper()

        var jsonString = respEntity.body?:""
        val jsonOpen = jsonString.indexOf("{")
        val jsonClose = jsonString.lastIndex - 1

        jsonString = jsonString.substring(jsonOpen, jsonClose)

        val jsonNode = objectMapper.readTree(jsonString)

        val errorCount = jsonNode.get("message").get("result").get("errata_count").toString().toInt()
        val originFullText = jsonNode.get("message").get("result").get("origin_html").toString()
        val fixedFullText = jsonNode.get("message").get("result").get("html").toString()

        val originTextArray = originFullText.split("</span>")
        val fixedTextArray = fixedFullText.split("</span>")

        return Optional.fromNullable(Array(errorCount){
            val errorText = originTextArray[it].substring(originTextArray[it].indexOf(">") + 1)
            val fixedText = fixedTextArray[it].substring(fixedTextArray[it].indexOf(">") + 1)
            GrammarDto(errorText, fixedText)
        })
    }
}