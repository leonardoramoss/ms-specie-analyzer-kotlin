package io.species.analyzer.application

import io.species.analyzer.domain.event.DomainEvent
import io.species.analyzer.domain.event.EventNotifier
import io.species.analyzer.domain.event.SpecieAnalyzedEvent
import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.domain.specie.analyzer.Analyzer
import io.species.analyzer.domain.specie.stats.StatsExecutor
import io.species.analyzer.domain.specie.stats.StatsIdentifier
import io.species.analyzer.domain.specie.stats.StatsResult
import io.species.analyzer.infrastructure.annotation.ApplicationService
import io.species.analyzer.infrastructure.exception.SpecieAnalyzerNotFoundException
import io.species.analyzer.infrastructure.exception.StatsExecutorException
import io.species.analyzer.infrastructure.fetcher.Fetcher
import java.util.EnumMap

@ApplicationService
class SpecieAnalyzerApplicationService(val fetchSpecieAnalyzed: Fetcher<SpecieAnalysis, SpecieAnalysis>,
                                       val analyzers: Map<SpecieIdentifier, Analyzer> = EnumMap(SpecieIdentifier::class.java),
                                       val executors: Map<StatsIdentifier, StatsExecutor<*>> = EnumMap(StatsIdentifier::class.java),
                                       val eventNotifier: EventNotifier<DomainEvent<*>>) {

    fun analyze(specieAnalysis: SpecieAnalysis) : SpecieAnalysis? {
        val analysis = fetchSpecieAnalyzed.fetch(specieAnalysis)

        return analysis?: let {
            val analyzer = analyzers[specieAnalysis.expectedIdentifier]
                ?: throw SpecieAnalyzerNotFoundException("There are no analyzer for this specie: ${specieAnalysis.expectedIdentifier}")

            val specieAnalyzed = analyzer.analyze(specieAnalysis)
            eventNotifier.notify(SpecieAnalyzedEvent(specieAnalyzed))
            specieAnalyzed
        }
    }

    fun viewStats(statsIdentifier: StatsIdentifier): StatsResult<*>? =
        executors[statsIdentifier]
            ?.execute() ?: throw StatsExecutorException("There are no stats executor configured for: ${statsIdentifier.name}")
}
