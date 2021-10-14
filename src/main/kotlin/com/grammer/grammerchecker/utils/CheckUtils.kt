package com.grammer.grammerchecker.utils

interface CheckUtils<T> {
    fun required(value : T, name : String)
    fun lessThen(value : T, size : Int, name : String)
}