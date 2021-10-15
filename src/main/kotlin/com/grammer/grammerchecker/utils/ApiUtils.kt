package com.grammer.grammerchecker.utils

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.http.HttpStatus

class ApiUtils {
    companion object{
        fun <T> success(response: T): ApiResult<T> =
            ApiResult(true, response, null)

        fun <T> error(throwable: Throwable, status: HttpStatus) : ApiResult<T> =
            ApiResult(false, null, ApiError(throwable, status))

        fun <T> error(message: String, status: HttpStatus) : ApiResult<T> =
            ApiResult(false, null, ApiError(message, status))

        class ApiError(private val message: String, httpStatus: HttpStatus){
            private val status = httpStatus.value()

            constructor(throwable: Throwable, status: HttpStatus) : this(throwable.message?:"", status)

            fun getMessage() = message
            fun getStatus() = status

            override fun toString() =
                ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("message", message)
                .append("status", status)
                .toString()
        }

        class ApiResult<T>(
            private val success: Boolean,
            private val response: T?,
            private val error: ApiError?
        ){

            fun isSuccess() =  success
            fun getError() = error
            fun getResponse() = response

            override fun toString() =
                ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("success", success)
                .append("response", response)
                .append("error", error)
                .toString()
        }
    }
}