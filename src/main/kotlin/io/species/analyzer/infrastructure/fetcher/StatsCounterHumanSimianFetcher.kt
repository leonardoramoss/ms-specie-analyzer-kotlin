package io.species.analyzer.infrastructure.fetcher

import io.species.analyzer.domain.specie.SpecieAnalysisCounterRepository
import io.species.analyzer.domain.specie.SpecieIdentifier
import org.springframework.stereotype.Component

@Component
class StatsCounterHumanSimianFetcher(
    private val specieAnalysisCounterRepository: SpecieAnalysisCounterRepository
) : Fetcher<List<SpecieIdentifier>, Map<SpecieIdentifier, Long>> {

    override fun fetch(argument: List<SpecieIdentifier>?): Map<SpecieIdentifier, Long>? {
        val speciesAnalysisCounters = argument?.let { specieAnalysisCounterRepository.findAllBySpecieIdentifierIn(it) }

        return speciesAnalysisCounters?.mapNotNull { it ->
            if (it.specieIdentifier != null && it.counter != null) {
                it.specieIdentifier to it.counter
            } else null
        }?.toMap()
    }
}
