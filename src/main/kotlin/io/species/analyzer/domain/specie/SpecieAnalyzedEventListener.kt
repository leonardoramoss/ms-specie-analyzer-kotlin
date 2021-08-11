package io.species.analyzer.domain.specie

import io.species.analyzer.domain.event.EventListener
import io.species.analyzer.domain.event.SpecieAnalyzedEvent
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.springframework.stereotype.Component

@Component
class SpecieAnalyzedEventListener(
    private val repository: SpecieAnalysisRepository,
    private val uuidGenerator: UUIDGenerator<SpecieAnalysis>
) : EventListener<SpecieAnalyzedEvent> {

    override fun onEvent(event: SpecieAnalyzedEvent) {
        val specieAnalysis = event.source.withUUID(uuidGenerator)
        repository.save(specieAnalysis)
    }
}
