package io.species.analyzer.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "DNA is not from a simian")
class SimianException(override val message: String) : SpecieAnalysisException(message)