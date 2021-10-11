package com.grammer.grammerchecker.grammar_checker

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.web.bind.annotation.GetMapping

import com.grammer.grammerchecker.utils.ApiUtils.Companion.ApiResult
import com.grammer.grammerchecker.utils.ApiUtils.Companion.success
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api")
class GrammarCheckController(val service: GrammarCheckService) {

    @GetMapping("check")
    fun checkSpell() : ApiResult<JsonNode>{
        val result = service.checkGrammar("되요")

        return success(result)
    }
}