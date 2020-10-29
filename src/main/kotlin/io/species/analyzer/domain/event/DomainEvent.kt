package io.species.analyzer.domain.event

import java.util.EventObject

abstract class DomainEvent<T>(value: T) : EventObject(value) {

    override fun getSource(): T = super.getSource() as T

    abstract fun eventType(): EventType
}