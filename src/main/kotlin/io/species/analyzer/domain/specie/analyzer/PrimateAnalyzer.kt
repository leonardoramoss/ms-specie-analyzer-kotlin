package io.species.analyzer.domain.specie.analyzer

import io.species.analyzer.domain.specie.DNA
import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieIdentifier
import io.species.analyzer.domain.specie.analyzer.manipulation.DNAManipulation
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class PrimateAnalyzer : Analyzer {

    override fun analyze(specieAnalysis: SpecieAnalysis): SpecieAnalysis {
        return if(isSimian(specieAnalysis.originalDNA())) {
            specieAnalysis.markIdentifiedAs(SpecieIdentifier.SIMIAN)
        } else {
            specieAnalysis.markIdentifiedAs(SpecieIdentifier.HUMAN)
        }
    }

    /**
     *
     */
    private fun isSimian(dna: Array<DNA>) : Boolean {
        val pattern = Pattern.compile(".*(A{4}|C{4}|G{4}|T{4}).*")
        val dnaManipulation = DNAManipulation(dna)

        return isSimianByPattern(dna, pattern) ||
            isSimianByPattern(dnaManipulation.transposeDNASequence(), pattern) ||
            isSimianByPattern(dnaManipulation.transposeDiagonalDNASequence(), pattern) ||
            isSimianByPattern(dnaManipulation.transposeReversedDiagonalDNASequence(), pattern)
    }

    private fun isSimianByPattern(dna: Array<DNA>, pattern: Pattern) : Boolean = dna.any { pattern.matcher(it).matches() }
}