package io.species.analyzer.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Failed deserialization")
class SpecieAnalysisDeserializationException(message: String) : SpecieAnalysisException(message)
