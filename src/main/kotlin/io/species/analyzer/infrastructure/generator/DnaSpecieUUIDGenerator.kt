package io.species.analyzer.infrastructure.generator

import io.species.analyzer.domain.specie.SpecieAnalysis
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DnaSpecieUUIDGenerator : UUIDGenerator<SpecieAnalysis> {

    override fun generate(speciesAnalysis: SpecieAnalysis?): UUID =
        speciesAnalysis?.let { UUID.nameUUIDFromBytes(it.dna.toByteArray()) }
            ?: TODO()
}
