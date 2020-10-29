package io.species.analyzer.infrastructure.serialization.serializer

import io.species.analyzer.infrastructure.context.DefaultApplicationContextHolder
import io.species.analyzer.presentation.wrapper.SpecieAnalysisStatsWrapper

class SpecieAnalysisStatsWrapperSerializer : AbstractSerializer<SpecieAnalysisStatsWrapper>() {

    private val serializers = DefaultApplicationContextHolder.getBean("serializers") as Map<Class<*>, AbstractSerializer<Any>>?

    override fun serialize(value: SpecieAnalysisStatsWrapper?, jsonWriter: JsonWriter) {
        value?.let {
            it.stats.let { stats ->
                val javaClass = stats.javaClass
                val serializer = serializers?.get(javaClass)
                serializer?.serialize(stats, jsonWriter)
            }
        }
    }
}