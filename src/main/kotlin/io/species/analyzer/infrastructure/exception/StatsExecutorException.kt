package io.species.analyzer.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Stats executor not found")
class StatsExecutorException(override val message: String) : SpecieAnalysisException(message)