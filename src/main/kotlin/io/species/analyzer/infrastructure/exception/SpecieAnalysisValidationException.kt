package io.species.analyzer.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Validation error")
class SpecieAnalysisValidationException(message: String) : SpecieAnalysisException(message)