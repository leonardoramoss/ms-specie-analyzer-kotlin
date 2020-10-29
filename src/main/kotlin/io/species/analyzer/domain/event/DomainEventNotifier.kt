package io.species.analyzer.domain.event

import org.springframework.stereotype.Component

@Component
class DomainEventNotifier(val eventListener: Map<EventType, List<EventListener<DomainEvent<*>>>>?) : EventNotifier<DomainEvent<*>> {

    override fun notify(event: DomainEvent<*>) {
        eventListener?.let { it[event.eventType()]?.forEach { listener -> listener.onEvent(event)} }
    }
}