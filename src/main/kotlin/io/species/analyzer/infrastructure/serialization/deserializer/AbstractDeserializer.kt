package io.species.analyzer.infrastructure.serialization.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.ObjectCodec
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import io.species.analyzer.infrastructure.exception.SpecieAnalysisDeserializationException
import io.species.analyzer.infrastructure.exception.SpecieAnalysisException
import io.species.analyzer.infrastructure.serialization.SerializationLabel
import java.lang.Exception
import java.util.Objects

abstract class AbstractDeserializer<T> : JsonDeserializer<T>() {

    private val objectMapper = ObjectMapper()

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T {
        var jsonNode: JsonNode = JsonNodeFactory.instance.objectNode()

        return try {
            val codec: ObjectCodec = p.codec
            jsonNode = codec.readTree(p)
            deserialize(jsonNode)
        } catch (e: SpecieAnalysisException) {
            throw e
        } catch (e: Exception) {
            throw SpecieAnalysisDeserializationException("Cannot deserializer ${jsonNode}. Is not a valid payload.")
        }
    }

    abstract fun deserialize(jsonNode: JsonNode): T

    protected open fun <R> readFieldAs(jsonNode: JsonNode, serializationLabel: SerializationLabel, type: Class<R>): R {
        return objectMapper.convertValue(readJsonNodeField(jsonNode, serializationLabel), type)
    }

    protected open fun readJsonNodeField(node: JsonNode, serializationLabel: SerializationLabel): JsonNode? {
        return if (hasNonNull(node, serializationLabel)) node.findValue(serializationLabel.label()) else null
    }

    private fun hasNonNull(node: JsonNode, serializationLabel: SerializationLabel): Boolean {
        return has(node, serializationLabel) && node.hasNonNull(serializationLabel.label())
    }

    private fun has(node: JsonNode, serializationLabel: SerializationLabel): Boolean {
        return node.isNull.not() && node.has(serializationLabel.label())
    }
}