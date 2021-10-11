package com.grammer.grammerchecker.grammar_checker

import org.springframework.web.bind.annotation.GetMapping

import com.grammer.grammerchecker.utils.ApiUtils.Companion.ApiResult
import com.grammer.grammerchecker.utils.ApiUtils.Companion.success
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api")
class GrammarCheckController {

    @GetMapping("check")
    fun checkSpell() : ApiResult<String>{
        return success("test")
    }
}