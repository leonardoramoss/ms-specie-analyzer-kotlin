package io.species.analyzer.presentation.wrapper

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.infrastructure.serialization.deserializer.SpecieAnalysisWrapperDeserializer

@JsonDeserialize(using = SpecieAnalysisWrapperDeserializer::class)
class SpecieAnalysisWrapper(val specieAnalysis: SpecieAnalysis?)
