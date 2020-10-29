package io.species.analyzer.infrastructure.serialization.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.core.GenericTypeResolver
import java.io.IOException

abstract class AbstractSerializer<T> : JsonSerializer<T>() {

    override fun serialize(value: T?, gen: JsonGenerator, serializers: SerializerProvider) {
        serialize(value, JsonWriter(gen))
    }

    @Throws(IOException::class)
    abstract fun serialize(value: T?, jsonWriter: JsonWriter)

    open fun type(): Class<T> {
        return GenericTypeResolver.resolveTypeArgument(
            javaClass,
            AbstractSerializer::class.java
        ) as Class<T>
    }
}
