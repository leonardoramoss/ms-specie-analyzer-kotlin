package io.species.analyzer.infrastructure.generator

interface Generator<in T, out R> {

    fun generate(argument: T?): R
}
