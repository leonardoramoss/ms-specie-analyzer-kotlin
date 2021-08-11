package io.species.analyzer.infrastructure.serialization.serializer

import com.fasterxml.jackson.core.JsonGenerator
import io.species.analyzer.infrastructure.serialization.SerializationLabel
import java.math.BigDecimal

class JsonWriter(private val generator: JsonGenerator) {

    fun writeStartObject() = generator.writeStartObject()

    fun writeNumberField(serializationLabel: SerializationLabel, value: BigDecimal?) =
        value?.let { generator.writeNumberField(serializationLabel.label(), it) }

    fun writeNumberField(serializationLabel: SerializationLabel, value: Long?) =
        value?.let { generator.writeNumberField(serializationLabel.label(), it) }

    fun writeStringField(serializationLabel: SerializationLabel, value: String?) =
        value?.let { generator.writeStringField(serializationLabel.label(), it) }

    fun writeEndObject() = generator.writeEndObject()
}
