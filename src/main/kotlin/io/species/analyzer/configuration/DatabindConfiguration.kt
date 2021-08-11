package io.species.analyzer.configuration

import io.species.analyzer.infrastructure.serialization.serializer.AbstractSerializer
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DatabindConfiguration {

    @Bean
    fun serializers(serializers: List<AbstractSerializer<*>> = listOf()): Map<Class<*>, AbstractSerializer<*>> {
        return serializers.associateBy { it.type() }
    }
}
