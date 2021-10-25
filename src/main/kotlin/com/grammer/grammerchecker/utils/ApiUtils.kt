package com.grammer.grammerchecker.utils

data class ApiUtils<T>(
    val success: Boolean = true,
    val response: T? = null,
    val error: Map<String, Any>? = null
)