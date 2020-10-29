package io.species.analyzer.presentation.rest

import io.species.analyzer.application.SpecieAnalyzerApplicationService
import io.species.analyzer.domain.specie.stats.StatsIdentifier
import io.species.analyzer.presentation.wrapper.SpecieAnalysisStatsWrapper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class SpecieAnalysisStatsController(val applicationService: SpecieAnalyzerApplicationService) {

    @RequestMapping(value = ["/stats"], method = [RequestMethod.GET])
    fun stats(): SpecieAnalysisStatsWrapper? =
        this.applicationService.viewStats(StatsIdentifier.RATIO_SIMIAN_HUMAN)
            ?.let { SpecieAnalysisStatsWrapper(it) }
}
