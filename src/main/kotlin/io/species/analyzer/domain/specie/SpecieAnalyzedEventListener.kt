package io.species.analyzer.domain.specie

import io.species.analyzer.domain.event.EventListener
import io.species.analyzer.domain.event.SpecieAnalyzedEvent
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.springframework.stereotype.Component

@Component
class SpecieAnalyzedEventListener(val repository: SpecieAnalysisRepository,
                                  val uuidGenerator: UUIDGenerator<SpecieAnalysis>) : EventListener<SpecieAnalyzedEvent> {

    override fun onEvent(specieAnalyzedEvent: SpecieAnalyzedEvent) {
        val specieAnalysis = specieAnalyzedEvent.source.withUUID(uuidGenerator)
        repository.save(specieAnalysis)
    }
}
