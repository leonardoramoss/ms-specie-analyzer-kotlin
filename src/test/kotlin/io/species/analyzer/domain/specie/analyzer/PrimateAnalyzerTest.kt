package io.species.analyzer.domain.specie.analyzer

import io.species.analyzer.domain.specie.SpecieAnalysis
import io.species.analyzer.domain.specie.SpecieIdentifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PrimateAnalyzerTest {

    private val analyzer: PrimateAnalyzer = PrimateAnalyzer()

    private val HORIZONTAL_SIMIAN = arrayOf("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG")
    private val VERTICAL_SIMIAN = arrayOf("CTTAGA", "CTATGC", "CGTCTT", "ACACGT", "CCTCTA", "TCACTG")
    private val DIAGONAL_SIMIAN = arrayOf("CTTAGA", "CTATGC", "CATCTT", "ACACGT", "CCTGTA", "TCACTG")
    private val REVERSED_DIAGONAL_SIMIAN = arrayOf("CTTAGA", "CTATGC", "CTTCTT", "ACACGT", "CCTGTA", "TCACTG")

    private val HUMAN = arrayOf("ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG")

    @Test
    fun `given horizontal simian DNA sequence and is expected as a simian, then should be matches as expected`() {
        val speciesAnalysis = SpecieAnalysis(dna = HORIZONTAL_SIMIAN).markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)

        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalyzed.identifier)
        assertTrue(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }

    @Test
    fun `given vertical simian DNA sequence and is expected as a simian, then should be matches as expected`() {
        val speciesAnalysis = SpecieAnalysis(dna = VERTICAL_SIMIAN).markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalyzed.identifier)
        assertTrue(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }

    @Test
    fun `given diagonal simian DNA sequence and is expected as a simian, then should matches as expected`() {
        val speciesAnalysis = SpecieAnalysis(dna = DIAGONAL_SIMIAN).markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalyzed.identifier)
        assertTrue(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }

    @Test
    fun `given reversed diagonal simian DNA sequence and expected as a simian, then should be matches as expected`() {
        val speciesAnalysis = SpecieAnalysis(dna = REVERSED_DIAGONAL_SIMIAN).markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalyzed.identifier)
        assertTrue(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }

    @Test
    fun `given human DNA sequence and expected as a human, then should be matches as expected`() {
        val speciesAnalysis = SpecieAnalysis(dna = HUMAN).markExpectedIdentifierAs(SpecieIdentifier.HUMAN)
        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.HUMAN, speciesAnalyzed.identifier)
        assertTrue(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }

    @Test
    fun `given human DNA sequence and expected as simian, then should be not matches`() {
        val speciesAnalysis = SpecieAnalysis(dna = HUMAN).markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
        val speciesAnalyzed = analyzer.analyze(speciesAnalysis)

        assertEquals(SpecieIdentifier.HUMAN, speciesAnalyzed.identifier)
        assertFalse(speciesAnalyzed.isIdentifierMatchesAsExpected())
        assertNotNull(speciesAnalyzed.analyzedAt)
    }
}
