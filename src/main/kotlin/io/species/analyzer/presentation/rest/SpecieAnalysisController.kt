package io.species.analyzer.presentation.rest

import io.species.analyzer.application.SpecieAnalyzerApplicationService
import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.infrastructure.exception.SimianException
import io.species.analyzer.presentation.wrapper.SpecieAnalysisWrapper
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class SpecieAnalysisController(val applicationService: SpecieAnalyzerApplicationService) {

    @RequestMapping(value = ["/simian"], method = [RequestMethod.POST])
    fun simian(@RequestBody specieAnalysisWrapper: SpecieAnalysisWrapper) {
        specieAnalysisWrapper.specieAnalysis
            ?.markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
            ?.let { applicationService.analyze(it) }
            ?.takeIf { it.isIdentifierMatchesAsExpected() }
            ?: throw SimianException("Is not a simian")
    }
}
