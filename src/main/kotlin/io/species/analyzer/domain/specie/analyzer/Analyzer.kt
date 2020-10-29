package io.species.analyzer.domain.specie.analyzer

import io.species.analyzer.domain.specie.SpecieAnalysis

interface Analyzer {

    fun analyze(specieAnalysis: SpecieAnalysis) : SpecieAnalysis
}