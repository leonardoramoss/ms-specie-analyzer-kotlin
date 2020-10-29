package io.species.analyzer.infrastructure.adapter

import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieAnalysisCounter
import org.springframework.stereotype.Component

@Component
class SpeciesAnalysisCounterAdapter : Adapter<SpecieAnalysis, SpecieAnalysisCounter> {

    override fun adapt(specieAnalysis: SpecieAnalysis): SpecieAnalysisCounter =
        SpecieAnalysisCounter(specieIdentifier = specieAnalysis.identifier)
}