package io.species.analyzer.domain.specie.analyzer.manipulation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class DNAManipulationTest {

    private val DNA_SEQUENCE = arrayOf("AAAA", "BBBB", "CCCC", "DDDD")
    private val EXPECTED_TRANSPOSED_DNA_SEQUENCE = arrayOf("ABCD", "ABCD", "ABCD", "ABCD")
    private val DIAGONAL_DNA_SEQUENCE = arrayOf("ABCD", "BCDA", "CDAB", "DABC")
    private val EXPECTED_DIAGONAL_DNA_SEQUENCE = arrayOf("A", "BB", "CCC", "DDDD", "AAA", "BB", "C")
    private val EXPECTED_REVERSED_DIAGONAL_DNA_SEQUENCE = arrayOf("D", "AC", "BDB", "CACA", "BDB", "AC", "D")

    @Test
    fun `transpose DNA sequence`() {
        val dnaManipulation = DNAManipulation(DNA_SEQUENCE)
        Assertions.assertEquals(EXPECTED_TRANSPOSED_DNA_SEQUENCE.joinToString(), dnaManipulation.transposeDNASequence().joinToString())
    }

    @Test
    fun `transpose diagonal DNA sequence`() {
        val dnaManipulation = DNAManipulation(DIAGONAL_DNA_SEQUENCE)
        Assertions.assertEquals(EXPECTED_DIAGONAL_DNA_SEQUENCE.joinToString(), dnaManipulation.transposeDiagonalDNASequence().joinToString());
    }

    @Test
    fun `transpose reversed diagonal DNA sequence`() {
        val dnaManipulation = DNAManipulation(DIAGONAL_DNA_SEQUENCE)
        Assertions.assertEquals(EXPECTED_REVERSED_DIAGONAL_DNA_SEQUENCE.joinToString(), dnaManipulation.transposeReversedDiagonalDNASequence().joinToString());
    }
}
