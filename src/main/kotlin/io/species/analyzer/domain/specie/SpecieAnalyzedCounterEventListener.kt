package io.species.analyzer.domain.specie

import io.species.analyzer.domain.event.EventListener
import io.species.analyzer.domain.event.SpecieAnalyzedEvent
import io.species.analyzer.infrastructure.command.IncrementSpecieAnalyzedCounterCommand
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService

@Component
class SpecieAnalyzedCounterEventListener(val executorService: ExecutorService,
                                         val incrementSpecieAnalyzedCounterCommand: IncrementSpecieAnalyzedCounterCommand) :
    EventListener<SpecieAnalyzedEvent> {

    override fun onEvent(event: SpecieAnalyzedEvent) {
        executorService.execute { incrementSpecieAnalyzedCounterCommand.execute(event.source) }
    }
}