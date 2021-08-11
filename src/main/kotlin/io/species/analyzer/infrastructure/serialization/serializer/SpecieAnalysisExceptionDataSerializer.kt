package io.species.analyzer.infrastructure.serialization.serializer

import io.species.analyzer.infrastructure.exception.handler.SpecieAnalysisExceptionData
import io.species.analyzer.infrastructure.serialization.SerializationLabel
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class SpecieAnalysisExceptionDataSerializer : AbstractSerializer<SpecieAnalysisExceptionData>() {

    override fun serialize(value: SpecieAnalysisExceptionData?, jsonWriter: JsonWriter) {
        value?.let {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            jsonWriter.writeStartObject()
            jsonWriter.writeStringField(SpecieExceptionDataLabels.CODE, it.httpStatus.toString())
            jsonWriter.writeStringField(SpecieExceptionDataLabels.TIMESTAMP, formatter.format(it.timestamp))
            jsonWriter.writeStringField(SpecieExceptionDataLabels.CAUSE, it.cause)
            jsonWriter.writeStringField(SpecieExceptionDataLabels.MESSAGE, it.message)
            jsonWriter.writeEndObject()
        }
    }

    private enum class SpecieExceptionDataLabels(private val label: String) : SerializationLabel {

        CODE("code"),
        CAUSE("cause"),
        TIMESTAMP("timestamp"),
        MESSAGE("message");

        override fun label(): String = label
    }
}
