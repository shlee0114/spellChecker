package com.grammer.grammerchecker.utils

import org.springframework.transaction.annotation.Transactional

open class StringCheckUtils : CheckUtils<String> {

    @Transactional
    override fun required(value: String, name: String) {
        if(value.isEmpty())
            throw IllegalArgumentException("$name is required")
    }

    @Transactional
    override fun lessThen(value: String, size: Int, name: String) {
        if(value.length > size){
            throw  IllegalArgumentException("$name length must be less than $size characters")
        }
    }


}