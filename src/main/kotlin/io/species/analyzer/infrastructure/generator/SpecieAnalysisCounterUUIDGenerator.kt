package io.species.analyzer.infrastructure.generator

import io.species.analyzer.domain.specie.SpecieAnalysisCounter
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SpecieAnalysisCounterUUIDGenerator : UUIDGenerator<SpecieAnalysisCounter> {

    override fun generate(specieAnalysisCounter: SpecieAnalysisCounter?): UUID {
        return specieAnalysisCounter
            ?.let { it.specieIdentifier.toString() }
            ?.let { UUID.nameUUIDFromBytes(it.toByteArray()) }
            ?: TODO()
    }
}