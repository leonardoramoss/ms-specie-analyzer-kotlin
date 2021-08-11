package io.species.analyzer.domain.specie.stats

interface StatsExecutor<T> where T : StatsResult<*> {

    fun execute(): T
}
