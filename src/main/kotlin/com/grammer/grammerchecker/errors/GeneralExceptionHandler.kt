package com.grammer.grammerchecker.errors

import com.grammer.grammerchecker.utils.ApiUtils
import javassist.NotFoundException
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class GeneralExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    fun newResponse(throwable: Throwable, status : HttpStatus) =
        newResponse(throwable.message!!, status)

    fun newResponse(message : String, status : HttpStatus) : ResponseEntity<ApiUtils.Companion.ApiResult<Any>>{
        val header = HttpHeaders()

        header.add("Content-Type", "application/json")

        return ResponseEntity(ApiUtils.error(message, status), header, status)
    }

    @ExceptionHandler(
        NoHandlerFoundException::class,
        NotFoundException::class
    )
    fun handleNotFoundException(e : Exception) = newResponse(e, HttpStatus.NOT_FOUND)

    @ExceptionHandler(
        IllegalArgumentException::class,
        IllegalStateException::class,
        ConstraintViolationException::class,
        MethodArgumentNotValidException::class
    )
    fun handleBadRequestException(e : Exception) : ResponseEntity<*>{
        log.debug("Bad request exception occurred : {}", e.message, e)

        return if (e is MethodArgumentNotValidException) {
            newResponse(
                e.bindingResult.allErrors[0].defaultMessage!!,
                HttpStatus.BAD_REQUEST
            )
        } else newResponse(e, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(HttpMediaTypeException::class)
    fun handleHttpMediaTypeException(e: Exception?): ResponseEntity<*>? {
        return newResponse(e!!, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotAllowedException(e: Exception?): ResponseEntity<*>? {
        return newResponse(e!!, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(Exception::class, RuntimeException::class)
    fun handleException(e: Exception): ResponseEntity<*>? {
        log.error("Unexpected exception occurred: {}", e.message, e)
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}