package com.grammer.grammerchecker.grammar_checker

import com.fasterxml.jackson.databind.JsonNode

import com.grammer.grammerchecker.utils.ApiUtils.Companion.ApiResult
import com.grammer.grammerchecker.utils.ApiUtils.Companion.success
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api")
class GrammarCheckController(val service: GrammarCheckService) {

    @PostMapping("check")
    fun checkAllSpell(@RequestParam("text", required = false) text : String?) : ApiResult<JsonNode>{
        try{
            val result = service.checkGrammar(text?:"")

            return success(result)
        }catch (e : IllegalArgumentException){
            throw IllegalArgumentException(e.message, e)
        }
    }
}