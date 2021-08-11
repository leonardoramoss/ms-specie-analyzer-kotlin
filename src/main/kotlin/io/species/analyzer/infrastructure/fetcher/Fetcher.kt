package io.species.analyzer.infrastructure.fetcher

interface Fetcher<in T, out R> {

    fun fetch(argument: T?): R?
}
