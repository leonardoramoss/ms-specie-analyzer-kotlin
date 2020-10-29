package io.species.analyzer.infrastructure.exception.handler

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.species.analyzer.infrastructure.serialization.serializer.SpecieAnalysisExceptionDataSerializer
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonSerialize(using = SpecieAnalysisExceptionDataSerializer::class)
data class SpecieAnalysisExceptionData(
    val cause: String,
    val message: String? = null,
    val httpStatus: HttpStatus,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
