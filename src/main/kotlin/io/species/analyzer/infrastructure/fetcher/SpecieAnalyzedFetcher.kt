package io.species.analyzer.infrastructure.fetcher

import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieAnalysisRepository
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.springframework.stereotype.Component

@Component
class SpecieAnalyzedFetcher(val specieAnalysisRepository: SpecieAnalysisRepository,
                            val uuidGenerator: UUIDGenerator<SpecieAnalysis>) : Fetcher<SpecieAnalysis, SpecieAnalysis> {

    override fun fetch(specieAnalysis: SpecieAnalysis?): SpecieAnalysis? =
        specieAnalysisRepository.findById(uuidGenerator.generate(specieAnalysis)).orElse(null)
}