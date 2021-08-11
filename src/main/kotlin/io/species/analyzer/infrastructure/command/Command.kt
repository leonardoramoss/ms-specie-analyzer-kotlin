package io.species.analyzer.infrastructure.command

interface Command<T> {

    fun execute(argument: T)
}
