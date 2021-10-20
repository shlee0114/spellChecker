package com.grammer.grammerchecker.errors

import com.grammer.grammerchecker.utils.ApiUtils
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

class GlobalErrorAttribute : DefaultErrorAttributes() {

    override fun getErrorAttributes(request: ServerRequest?, options: ErrorAttributeOptions?): MutableMap<String, Any> {
        val map = super.getErrorAttributes(request, options)
        val throwable = getError(request)

        return if (throwable is GlobalException) {
            val ex = getError(request) as GlobalException
            map.apply {
                put("exception", ex.javaClass.simpleName)
                put("message", ex.message)
                put("status", ex.status.value())
                put("error", ex.status.reasonPhrase)
            }
        }else{
            map.apply {
                put("exception", "SystemException")
                put("message", throwable.message)
            }
        }
    }

}