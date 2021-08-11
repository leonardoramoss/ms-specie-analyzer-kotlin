package io.species.analyzer.infrastructure.generator

import io.species.analyzer.domain.specie.SpecieAnalysisCounter
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SpecieAnalysisCounterUUIDGenerator : UUIDGenerator<SpecieAnalysisCounter> {

    override fun generate(argument: SpecieAnalysisCounter?): UUID {
        return argument
            ?.let { it.specieIdentifier.toString() }
            ?.let { UUID.nameUUIDFromBytes(it.toByteArray()) }
            ?: TODO()
    }
}
