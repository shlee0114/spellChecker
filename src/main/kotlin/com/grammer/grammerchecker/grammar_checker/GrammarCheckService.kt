package com.grammer.grammerchecker.grammar_checker

import com.fasterxml.jackson.databind.ObjectMapper
import com.grammer.grammerchecker.utils.StringCheckUtils
import graphql.com.google.common.base.Optional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class GrammarCheckService {

    @Value("\${naver.url}")
    private lateinit var naverUrl : String

    @Value("\${naver.user-gent}")
    private lateinit var naverUserGent : String

    @Value("\${naver.referer}")
    private lateinit var naverReferer : String

    @Transactional(readOnly = true)
    fun checkGrammar(grammar : String) : Optional<Array<GrammarDto>>{

        StringCheckUtils().required(grammar, "grammar")
        StringCheckUtils().lessThen(grammar, 500, "grammar")

        val header = HttpHeaders()
        header.set("user-gent", naverUserGent)
        header.set("referer", naverReferer)

        val entity = HttpEntity<String>("", header)

        val restTemplate = RestTemplate()
        val respEntity = restTemplate.exchange("$naverUrl$grammar", HttpMethod.GET, entity, String::class.java)

        val objectMapper = ObjectMapper()

        var jsonString = respEntity.body?:""
        val jsonOpen = jsonString.indexOf("{")
        val jsonClose = jsonString.lastIndex - 1

        jsonString = jsonString.substring(jsonOpen, jsonClose)

        val jsonNode = objectMapper.readTree(jsonString)

        val result = jsonNode.get("message").get("result")

        val errorCount = result.get("errata_count").toString().toInt()
        val originFullText = result.get("origin_html").toString()
        val fixedFullText = result.get("html").toString()

        val originTextArray = originFullText.split("</span>")
        val fixedTextArray = fixedFullText.split("</span>")

        return Optional.fromNullable(Array(errorCount){
            val errorText = originTextArray[it].substring(originTextArray[it].indexOf(">") + 1)
            val fixedText = fixedTextArray[it].substring(fixedTextArray[it].indexOf(">") + 1)
            GrammarDto(errorText, fixedText)
        })
    }
}