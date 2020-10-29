package io.species.analyzer.infrastructure.serialization.deserializer

import com.fasterxml.jackson.databind.JsonNode
import io.species.analyzer.domain.specie.DNA
import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.infrastructure.serialization.SerializationLabel
import io.species.analyzer.presentation.wrapper.SpecieAnalysisWrapper
import org.springframework.stereotype.Component

@Component
class SpecieAnalysisWrapperDeserializer : AbstractDeserializer<SpecieAnalysisWrapper>() {

    override fun deserialize(jsonNode: JsonNode): SpecieAnalysisWrapper {
        val dnaChain : Array<DNA> = readFieldAs(jsonNode, SpecieAnalysisLabels.DNA, Array<DNA>::class.java)
        return SpecieAnalysisWrapper(SpecieAnalysis(dna = dnaChain))
    }

    private enum class SpecieAnalysisLabels(private val label: String) : SerializationLabel {

        DNA("dna");

        override fun label(): String = label
    }
}