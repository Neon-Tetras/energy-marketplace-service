package me.olaosebikan.energy.marketplace.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionMapper {

    private val logger: Logger = LoggerFactory.getLogger(ExceptionMapper::class.java)

    @ExceptionHandler(value = [RecordNotFoundException::class])
    fun handleRecordNotFoundException(ex: RecordNotFoundException): ResponseEntity<Any?>? {
        logger.error("RecordNotFoundException: ", ex.message)
        val error = ErrorResponse("404", ex.message!!)
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [InvalidInputException::class])
    fun handleInvalidInputException(ex: InvalidInputException): ResponseEntity<Any?>? {
        logger.error("InvalidInputException: ", ex.message)
        val error = ErrorResponse("400", ex.message!!)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<Any?>? {
        logger.error("UnauthorizedException: ", ex.message)
        val error = ErrorResponse("401", ex.message!!)
        return ResponseEntity(error, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [BusinessException::class])
    fun handleBusinessException(ex: BusinessException): ResponseEntity<Any?>? {
        logger.error("BusinessException: ", ex.message)
        val error = ErrorResponse("500", ex.message!!)
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleUnknownException(ex: Exception): ResponseEntity<Any?>? {
        logger.error("Exception: ", ex.message)
        return ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}