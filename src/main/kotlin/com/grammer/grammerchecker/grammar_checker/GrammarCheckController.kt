package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.utils.ApiUtils.Companion.ApiResult
import com.grammer.grammerchecker.utils.ApiUtils.Companion.success
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("api")
class GrammarCheckController(val service: GrammarCheckService) {

    @GetMapping("check")
    fun checkAllSpell(
        @Valid
        @RequestParam
        grammar: GrammarRequest
    ) : ApiResult<Array<GrammarDto>> {
        try{
            val result = service.checkGrammar(grammar.text)

            return success(
                result.orElse(
                    arrayOf()
                )
            )
        }catch (e: Exception){
            throw Exception(e.message, e)
        }
    }

    @PostMapping("log")
    fun insertLog(
        @Valid
        @RequestBody
        log: LogRequest
    ) : ApiResult<Boolean> {
        try{
            service.insertSentenceLog(log)

            return success(true)
        }catch (e: Exception){
            throw Exception(e.message, e)
        }
    }
}