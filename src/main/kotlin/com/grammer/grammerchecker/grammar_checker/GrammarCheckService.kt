package com.grammer.grammerchecker.grammar_checker

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Preconditions
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
class GrammarCheckService {
    @Transactional(readOnly = true)
    fun checkGrammar(grammar : String) : JsonNode{

        if(grammar == "")
            throw IllegalArgumentException("grammar is required")

        Preconditions.checkArgument(
            grammar.length <= 500,
            "grammar length must be less than 500 characters"
        )

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

        return jsonNode.get("message").get("result")
    }
}