package io.species.analyzer.domain.event

interface EventListener<T> {

    fun onEvent(event: T)
}
