package com.grammer.grammerchecker.grammar_checker

import com.grammer.grammerchecker.utils.ApiUtils.Companion.ApiResult
import com.grammer.grammerchecker.utils.ApiUtils.Companion.success
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("api")
class GrammarCheckController(val service: GrammarCheckService) {

    @GetMapping("check")
    fun checkAllSpell(
        @Valid @RequestBody grammar: GrammarRequest
    ) : ApiResult<Array<GrammarDto>> {
        try{
            val result = service.checkGrammar(grammar.text)

            return success(
                result.orElse(
                    arrayOf()
                )
            )
        }catch (e: IllegalArgumentException){
            throw IllegalArgumentException(e.message, e)
        }
    }
}