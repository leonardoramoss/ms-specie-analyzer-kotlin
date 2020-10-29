package io.species.analyzer.domain.event

interface EventNotifier<T> {

    fun notify(event: T)
}