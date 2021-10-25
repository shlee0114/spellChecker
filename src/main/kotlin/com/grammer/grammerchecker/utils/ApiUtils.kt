package com.grammer.grammerchecker.utils

data class ApiUtils<T>(
    val success: Boolean = true,
    val response: T? = null,
    val error: ErrorUtils? = null
)

data class ErrorUtils(
    val message: String = "",
    val status: Int
)