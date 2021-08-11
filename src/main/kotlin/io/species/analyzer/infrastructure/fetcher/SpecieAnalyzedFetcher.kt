package io.species.analyzer.infrastructure.fetcher

import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieAnalysisRepository
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.springframework.stereotype.Component

@Component
class SpecieAnalyzedFetcher(
    private val specieAnalysisRepository: SpecieAnalysisRepository,
    private val uuidGenerator: UUIDGenerator<SpecieAnalysis>
) : Fetcher<SpecieAnalysis, SpecieAnalysis> {

    override fun fetch(argument: SpecieAnalysis?): SpecieAnalysis? =
        specieAnalysisRepository.findById(uuidGenerator.generate(argument)).orElse(null)
}
