package io.species.analyzer.domain.specie.stats

import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.infrastructure.fetcher.StatsCounterHumanSimianFetcher
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class StatsHumanSimianRatioExecutor(private val fetcher: StatsCounterHumanSimianFetcher) : StatsExecutor<StatsHumanSimianRatioResult> {

    override fun execute(): StatsHumanSimianRatioResult {
        val speciesCount = fetcher.fetch(listOf(SpecieIdentifier.HUMAN, SpecieIdentifier.SIMIAN))

        val humanCount = speciesCount?.get(SpecieIdentifier.HUMAN) ?: 0
        val simianCount = speciesCount?.get(SpecieIdentifier.SIMIAN) ?: 0

        val ratio = if (humanCount == 0L) {
            BigDecimal.valueOf(simianCount)
        } else {
            BigDecimal.valueOf((simianCount * 100.0f / humanCount).toDouble())
                .divide(BigDecimal.valueOf(100), 1, RoundingMode.HALF_UP)
        }

        return StatsHumanSimianRatioResult(humanCount, simianCount, ratio)
    }
}

data class StatsHumanSimianRatioResult(
    val humanCount: Long,
    val simianCount: Long,
    private val ratio: BigDecimal
) : StatsResult<BigDecimal> {
    override fun result(): BigDecimal = ratio
}
