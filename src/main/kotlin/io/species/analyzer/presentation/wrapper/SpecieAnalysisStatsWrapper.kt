package io.species.analyzer.presentation.wrapper

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.species.analyzer.domain.specie.stats.StatsResult
import io.species.analyzer.infrastructure.serialization.serializer.SpecieAnalysisStatsWrapperSerializer

@JsonSerialize(using = SpecieAnalysisStatsWrapperSerializer::class)
class SpecieAnalysisStatsWrapper(val stats: StatsResult<*>)