package io.species.analyzer.domain.event

import io.species.analyzer.domain.specie.SpecieAnalysis

class SpecieAnalyzedEvent(specieAnalysis: SpecieAnalysis) : DomainEvent<SpecieAnalysis>(specieAnalysis) {

    override fun eventType(): EventType = EventType.SPECIE_ANALYZED
}