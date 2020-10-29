package io.species.analyzer.configuration

import io.species.analyzer.domain.event.DomainEvent
import io.species.analyzer.domain.event.EventListener
import io.species.analyzer.domain.event.EventType
import io.species.analyzer.domain.specie.SpecieAnalyzedCounterEventListener
import io.species.analyzer.domain.specie.SpecieAnalyzedEventListener
import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.domain.specie.analyzer.Analyzer
import io.species.analyzer.domain.specie.analyzer.PrimateAnalyzer
import io.species.analyzer.domain.specie.stats.StatsExecutor
import io.species.analyzer.domain.specie.stats.StatsHumanSimianRatioExecutor
import io.species.analyzer.domain.specie.stats.StatsIdentifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
class ContextConfiguration {

    private val AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors()

    @Bean
    fun threadPool() : ExecutorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS)

    @Bean
    fun listeners(specieAnalyzedEventListener: SpecieAnalyzedEventListener,
                  specieAnalyzedCounterEventListener: SpecieAnalyzedCounterEventListener): Map<EventType, List<EventListener<DomainEvent<*>>>> =
        mapOf(EventType.SPECIE_ANALYZED to listOf(
            specieAnalyzedEventListener as EventListener<DomainEvent<*>>,
            specieAnalyzedCounterEventListener as EventListener<DomainEvent<*>>))

    @Bean
    fun analyzers(primateAnalyzer: PrimateAnalyzer): Map<SpecieIdentifier, Analyzer> =
        mapOf(SpecieIdentifier.SIMIAN to primateAnalyzer)

    @Bean
    fun statsExecutors(statsHumanSimianRatioExecutor: StatsHumanSimianRatioExecutor): Map<StatsIdentifier, StatsExecutor<*>> =
        mapOf(StatsIdentifier.RATIO_SIMIAN_HUMAN to statsHumanSimianRatioExecutor);
}
