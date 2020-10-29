package io.species.analyzer.infrastructure.serialization.serializer

import io.species.analyzer.domain.specie.stats.StatsHumanSimianRatioResult
import io.species.analyzer.infrastructure.context.DefaultApplicationContextHolder
import io.species.analyzer.infrastructure.serialization.SerializationLabel
import org.springframework.stereotype.Component

@Component
class StatsHumanSimianRatioResultSerializer : AbstractSerializer<StatsHumanSimianRatioResult>() {

    override fun serialize(value: StatsHumanSimianRatioResult?, jsonWriter: JsonWriter) {
        value?.let {
            jsonWriter.writeStartObject()
            jsonWriter.writeNumberField(StatsHumanSimianRatioLabel.SIMIAN, it.simianCount)
            jsonWriter.writeNumberField(StatsHumanSimianRatioLabel.HUMAN, it.humanCount)
            jsonWriter.writeNumberField(StatsHumanSimianRatioLabel.RATIO, it.result())
            jsonWriter.writeEndObject()
        }
    }

    private enum class StatsHumanSimianRatioLabel(private val label: String) : SerializationLabel {
        HUMAN("count_human_dna"),
        SIMIAN("count_mutant_dna"),
        RATIO("ratio");

        override fun label(): String = label
    }
}