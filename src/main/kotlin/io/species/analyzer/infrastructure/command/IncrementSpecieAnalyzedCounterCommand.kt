package io.species.analyzer.infrastructure.command

import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieAnalysisCounter
import io.species.analyzer.domain.specie.SpecieAnalysisCounterRepository
import io.species.analyzer.infrastructure.adapter.Adapter
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.springframework.stereotype.Component

@Component
class IncrementSpecieAnalyzedCounterCommand(
    private val specieAnalysisCounterRepository: SpecieAnalysisCounterRepository,
    private val specieAnalysisCounterUUIDGenerator: UUIDGenerator<SpecieAnalysisCounter>,
    private val specieAnalysisCounterAdapter: Adapter<SpecieAnalysis, SpecieAnalysisCounter>
) :
    Command<SpecieAnalysis> {

    @Synchronized override fun execute(argument: SpecieAnalysis) {
        val updatedSpeciesCounter = argument.identifier?.let { specieAnalysisCounterRepository.incrementSpecieCounter(it) }

        if (updatedSpeciesCounter != 1) {
            val specieAnalysisCounter = specieAnalysisCounterAdapter.adapt(argument)

            val analysisCounter = specieAnalysisCounter.copy(
                uuid = specieAnalysisCounterUUIDGenerator.generate(specieAnalysisCounter)
            )

            specieAnalysisCounterRepository.save(analysisCounter)
        }
    }
}
