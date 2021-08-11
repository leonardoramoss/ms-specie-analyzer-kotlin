package io.species.analyzer.infrastructure.exception.handler

import io.species.analyzer.infrastructure.exception.SimianException
import io.species.analyzer.infrastructure.exception.SpecieAnalysisDeserializationException
import io.species.analyzer.infrastructure.exception.SpecieAnalysisException
import io.species.analyzer.infrastructure.exception.SpecieAnalysisValidationException
import io.species.analyzer.infrastructure.exception.SpecieAnalyzerNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestControllerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        SpecieAnalysisValidationException::class,
        SpecieAnalysisDeserializationException::class,
        SimianException::class,
        SpecieAnalyzerNotFoundException::class,
        SpecieAnalysisException::class
    )
    protected fun handleSpecieAnalysisException(exception: SpecieAnalysisException) =
        handleException(exception)

    @ExceptionHandler(Exception::class)
    protected fun handleException(exception: Exception, httpServletRequest: HttpServletRequest): ResponseEntity<SpecieAnalysisExceptionData?>? {
        val uri = httpServletRequest.requestURI
        val rootCause = exception.cause
        val exceptionData = SpecieAnalysisExceptionData(exception.cause.toString(), "Opss!", HttpStatus.INTERNAL_SERVER_ERROR)
        logger.error("URI $uri - ${exception.message} - Root Cause: ${rootCause?.message}")
        return ResponseEntity(exceptionData, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun handleException(exception: SpecieAnalysisException): ResponseEntity<SpecieAnalysisExceptionData> {
        val annotation = exception.javaClass.getAnnotation(ResponseStatus::class.java)
        val message = exception.cause.takeIf { it != null }?.message ?: exception.message

        val specieAnalysisExceptionData = SpecieAnalysisExceptionData(annotation.reason, message, annotation.code)

        return ResponseEntity(specieAnalysisExceptionData, annotation.code)
    }
}
