package io.species.analyzer.configuration.fixtures

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import net.minidev.json.JSONValue
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class JsonFixture {

    companion object {
        private val objectMapper = ObjectMapper()

        fun loadJsonFile(path: String): String =
            JSONValue.parse(
                InputStreamReader(object {}.javaClass.getResourceAsStream("/$path"), StandardCharsets.UTF_8)
            ).toString()

        fun loadJsonFileAsJsonNode(path: String): JsonNode =
            try {
                objectMapper.readTree(loadJsonFile(path))
            } catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
    }
}
