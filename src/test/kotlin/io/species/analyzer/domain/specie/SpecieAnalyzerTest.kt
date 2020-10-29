package io.species.analyzer.domain.specie

import io.species.analyzer.infrastructure.exception.SpecieAnalysisValidationException
import io.species.analyzer.infrastructure.generator.DnaSpecieUUIDGenerator
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID

class SpecieAnalyzerTest {

    val generator: UUIDGenerator<SpecieAnalysis> = DnaSpecieUUIDGenerator()

    private val SIMIAN_DNA_SEQUENCE = arrayOf("ATCGCA", "TCTCCG", "TGGTTG", "CCTTTC", "GTAATC", "ACCACT")
    private val HUMAN_DNA_SEQUENCE = arrayOf("ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG")
    private val NOT_MINIMUM_NxN_DNA_SEQUENCE = arrayOf("ATC", "GCC", "ACC")
    private val NOT_A_NxN_DNA_SEQUENCE = arrayOf("ATCA", "GCCC", "TACC", "AAGC", "TTTTA")
    private val NOT_A_VALID_DNA_SEQUENCE_U_IS_NOT_VALID = arrayOf("ATCA", "GCCC", "TACC", "AUCA")

    val EXCEPTION_MESSAGE_DNA_NOT_VALID = { value: Any? -> "DNA sequence $value is invalid." }
    val EXCEPTION_MESSAGE_NOT_A_NxN_SEQUENCE = "There is not a NxN DNA sequence."
    val EXCEPTION_MESSAGE_PART_OF_DNA_SEQUENCE_NOT_VALID : (Any, Any) -> String = { value1, value2 -> "DNA sequence $value1 in $value2 is not valid." }


    @Test
    fun GivenASimianDNAAndIdentifierMarkedAsSimian_shouldBeAllMatches() {
        // "c0d7dab2-e85b-3029-94f7-0cb598b4e3e0"
        val expectedUUID = UUID.nameUUIDFromBytes(SIMIAN_DNA_SEQUENCE.joinToString("-").toByteArray())

        val speciesAnalysis =
            SpecieAnalysis(dna = SIMIAN_DNA_SEQUENCE)
                .markIdentifiedAs(SpecieIdentifier.SIMIAN)
                .withUUID(generator)

        assertEquals(SIMIAN_DNA_SEQUENCE.joinToString(), speciesAnalysis.originalDNA()?.joinToString())
        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalysis.expectedIdentifier)
        assertEquals(SpecieIdentifier.SIMIAN, speciesAnalysis.identifier)
        assertEquals(expectedUUID.toString(), speciesAnalysis.uuid.toString())
    }

    @Test
    fun GivenAHumanDNAAndIdentifierMarkedAsHuman_shouldBeAllMatches() {
        // "053a06a4-5b45-3e69-8f1c-cadf36bd0950"
        val expectedUUID = UUID.nameUUIDFromBytes(HUMAN_DNA_SEQUENCE.joinToString("-").toByteArray())

        val speciesAnalysis =
            SpecieAnalysis(dna = HUMAN_DNA_SEQUENCE)
                .markIdentifiedAs(SpecieIdentifier.HUMAN)
                .withUUID(generator)

        assertEquals(HUMAN_DNA_SEQUENCE.joinToString(), speciesAnalysis.originalDNA()?.joinToString())
        assertEquals(SpecieIdentifier.HUMAN, speciesAnalysis.expectedIdentifier)
        assertEquals(SpecieIdentifier.HUMAN, speciesAnalysis.identifier)
        assertEquals(expectedUUID.toString(), speciesAnalysis.uuid.toString())
    }

    @Test
    fun GivenAHumanDNA_whenExpectedIdentifierMarkedSimianAndHasIdentifierMarkedAsHuman_shouldBeMatchesReturnFalse() {
        val speciesAnalysis =
            SpecieAnalysis(dna = HUMAN_DNA_SEQUENCE)
                .markExpectedIdentifierAs(SpecieIdentifier.SIMIAN)
                .markIdentifiedAs(SpecieIdentifier.HUMAN)

        assertFalse(speciesAnalysis.isIdentifierMatchesAsExpected())
    }

    @Test
    fun GivenASimianDNA_whenExpectedIdentifierMarkedHumanAndHasIdentifierMarkedAsSimian_shouldBeMatchesReturnFalse() {
        val speciesAnalysis =
            SpecieAnalysis(dna = SIMIAN_DNA_SEQUENCE)
                .markExpectedIdentifierAs(SpecieIdentifier.HUMAN)
                .markIdentifiedAs(SpecieIdentifier.SIMIAN)

        assertFalse(speciesAnalysis.isIdentifierMatchesAsExpected())
    }

    @Test
    fun GivenAHumanDNA_whenExpectedIdentifierMarkedHumanAndHasIdentifierMarkedAsHuman_shouldBeMatchesReturnTrue() {
        val speciesAnalysis =
            SpecieAnalysis(dna = HUMAN_DNA_SEQUENCE)
                .markExpectedIdentifierAs(SpecieIdentifier.HUMAN)
                .markIdentifiedAs(SpecieIdentifier.HUMAN)

        assertTrue(speciesAnalysis.isIdentifierMatchesAsExpected())
    }

    @Test
    fun GivenAHumanDNA_whenExpectedIdentifierMarkedHumanAndHasNoIdentifierMarked_shouldBeMatchesReturnFalse() {
        val speciesAnalysis =
            SpecieAnalysis(dna = HUMAN_DNA_SEQUENCE)
                .markExpectedIdentifierAs(SpecieIdentifier.HUMAN)

        assertNull(speciesAnalysis.uuid)
        assertFalse(speciesAnalysis.isIdentifierMatchesAsExpected())
    }

    @Test
    fun GivenAHumanDNA_whenExpectedIdentifierMarkedHumanAndHasNoIdentifierMarked_shouldBeMatchesReturnFalse2() {
        val speciesAnalysis = SpecieAnalysis(dna = HUMAN_DNA_SEQUENCE)

        assertNull(speciesAnalysis.uuid)
        assertNull(speciesAnalysis.expectedIdentifier)
        assertFalse(speciesAnalysis.isIdentifierMatchesAsExpected())
    }

    @Nested
    inner class `Exception in specie analysis constructor` {

        @Test
        fun `when given a null DNA, should be throw SpecieAnalysisValidationException`() {
            val exception = assertThrows(SpecieAnalysisValidationException::class.java) { SpecieAnalysis.invoke() }
            assertEquals(EXCEPTION_MESSAGE_DNA_NOT_VALID(null), exception.message)
        }

        @Test
        fun `when given an empty DNA, should be throw SpecieAnalysisValidationException`() {
            val exception = assertThrows(SpecieAnalysisValidationException::class.java) { SpecieAnalysis.invoke(dna = arrayOf())}
            assertEquals(EXCEPTION_MESSAGE_DNA_NOT_VALID(arrayOf<DNA>().joinToString()), exception.message)
        }

        @Test
        fun `when given DNA don't have the minimum size, should be throw SpecieAnalysisValidationException`() {
            val exception = assertThrows(SpecieAnalysisValidationException::class.java) { SpecieAnalysis(dna = NOT_MINIMUM_NxN_DNA_SEQUENCE) }
            assertEquals(EXCEPTION_MESSAGE_DNA_NOT_VALID(NOT_MINIMUM_NxN_DNA_SEQUENCE.joinToString()), exception.message)
        }

        @Test
        fun `when given DNA is not a NxN, should be SpecieAnalysisValidationException`() {
            val exception = assertThrows(SpecieAnalysisValidationException::class.java) { SpecieAnalysis(dna = NOT_A_NxN_DNA_SEQUENCE) }
            assertEquals(EXCEPTION_MESSAGE_NOT_A_NxN_SEQUENCE, exception.message);
        }

        @Test
        fun `when given a not valid DNA sequence, should be throw SpecieAnalysisValidationException`() {
            val exception = assertThrows(SpecieAnalysisValidationException::class.java) { SpecieAnalysis(dna = NOT_A_VALID_DNA_SEQUENCE_U_IS_NOT_VALID) }
            assertEquals(EXCEPTION_MESSAGE_PART_OF_DNA_SEQUENCE_NOT_VALID("AUCA", NOT_A_VALID_DNA_SEQUENCE_U_IS_NOT_VALID.joinToString()), exception.message);
        }
    }
}
