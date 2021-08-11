package io.species.analyzer.infrastructure.adapter

interface Adapter<in T, out R> {

    fun adapt(argument: T): R
}
