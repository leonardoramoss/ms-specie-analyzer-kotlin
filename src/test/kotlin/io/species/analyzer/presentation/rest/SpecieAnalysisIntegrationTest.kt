package io.species.analyzer.presentation.rest

import com.fasterxml.jackson.databind.JsonNode
import io.species.analyzer.configuration.fixtures.DatabaseAssertion
import io.species.analyzer.configuration.fixtures.DatabaseFixture
import io.species.analyzer.configuration.fixtures.JsonFixture.Companion.loadJsonFile
import io.species.analyzer.configuration.fixtures.JsonFixture.Companion.loadJsonFileAsJsonNode
import io.species.analyzer.domain.specie.SpecieAnalysisRepository
import io.species.analyzer.domain.specie.SpecieIdentifier
import org.awaitility.Awaitility.await
import org.hamcrest.Matchers
import org.junit.FixMethodOrder
import org.junit.jupiter.api.Test
import org.junit.runners.MethodSorters
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.concurrent.TimeUnit

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.JVM)
internal class SpecieAnalysisIntegrationTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val databaseFixture: DatabaseFixture,
    @Autowired val specieAnalysisRepository: SpecieAnalysisRepository
) : DatabaseAssertion by databaseFixture {

    private val specieAnalysisTable = "SPECIE.SPECIES_ANALYSIS"
    private val specieAnalysisCounterTable = "SPECIE.SPECIES_ANALYSIS_COUNTER"

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidHorizontalSimianDna_whenPerformPost_shouldBeProcessedAndSavedOnDatabaseAndReturnStatusOk() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_horizontal_payload.json"), status().isOk)
        verifyDatabase("expected_simian_horizontal_specie.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_simian_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidVerticalSimianDna_whenPerformPost_shouldBeProcessedAndSavedOnDatabaseAndReturnStatusOk() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_vertical_payload.json"), status().isOk)
        verifyDatabase("expected_simian_vertical_species.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_simian_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidDiagonalSimianDna_whenPerformPost_shouldBeProcessedAndSavedOnDatabaseAndReturnStatusOk() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_diagonal_payload.json"), status().isOk)
        verifyDatabase("expected_simian_diagonal_species.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_simian_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidReversedDiagonalSimianDna_whenPerformPost_shouldBeProcessedAndSavedOnDatabaseAndReturnStatusOk() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_reversed_diagonal_payload.json"), status().isOk)
        verifyDatabase("expected_simian_reversed_diagonal_species.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_simian_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidHorizontalSimianDna_whenPerformPostAnAlreadySavedDna_shouldBeReturnFromDatabaseAndStatusOk() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_horizontal_payload.json"), status().isOk)

        verifyDatabase("expected_simian_horizontal_specie.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))

        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_simian_horizontal_payload.json"), status().isOk)

        verifyDatabase("expected_simian_horizontal_specie.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_simian_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAValidHumanDna_whenPerformPost_shouldBeProcessedAndSavedOnDatabaseAndReturnStatusForbidden() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/mock_human_payload.json"), status().isForbidden)
        verifyDatabase("expected_human_specie.xml", specieAnalysisTable, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("expected_human_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithNotAllowedCharacter_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/invalid/mock_invalid_not_allowed_character_payload.json"), status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithEmptyDNA_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/invalid/mock_invalid_empty_dna_payload.json"), status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithoutDNALabel_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/invalid/mock_invalid_without_dna_label_payload.json"), status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithNullDNA_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect("{ \"dna\": }", status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithNotANxNDNASequence_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/invalid/mock_invalid_not_a_NxN_payload.json"), status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenAInvalidPayloadWithLessNxNDNASequenceAllowed_whenPerformPost_shouldBeReturnStatusBadRequest() {
        performPostWithPayloadAndExpect(loadJsonFile("mock/invalid/mock_invalid_with_less_NxN_allowed_payload.json"), status().isBadRequest)
        verifyDatabase("expected_invalid_specie.xml", specieAnalysisTable)
        verifyDatabase("expected_empty_counter.xml", specieAnalysisCounterTable)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenBatchOfValidPayloads_whenPerformPost_shouldBeReturnStatusOkForSimiansAndForbiddenForHuman() {

        // Given
        val expectations = mapOf(SpecieIdentifier.HUMAN to status().isForbidden, SpecieIdentifier.SIMIAN to status().isOk)
        val mockPayloads = getPayloads("mock/mock_simian_payloads.json", "mock/mock_human_payloads.json")

        // When
        mockPayloads.parallelStream()
            .forEach {
                val speciesIdentifier = SpecieIdentifier.valueOf(it.get("species").asText())
                performPostWithPayloadAndExpect(it.get("payload").toString(), expectations[speciesIdentifier]!!)
            }

        await().pollInterval(200, TimeUnit.MILLISECONDS)
            .timeout(5000, TimeUnit.MILLISECONDS)
            .until({ specieAnalysisRepository.count() }, Matchers.equalTo(150L))

        val mvcResult = mockMvc.perform(get("/v1/stats")).andExpect(status().isOk).andReturn()

        // Then
        assertEquals(loadJsonFile("expected/response/stats/fullflow/expected_fullflow_stats.json"), mvcResult.response.contentAsString, false)

        val querySpeciesAnalysis = "SELECT * FROM $specieAnalysisTable ORDER BY UUID"
        val querySpeciesAnalysisCounter = "SELECT * FROM $specieAnalysisCounterTable ORDER BY SPECIE"

        verifyDatabase("fullflow/expected_valid_human_and_simian_payloads.xml", specieAnalysisTable, query = querySpeciesAnalysis, ignoredColumns = arrayOf("ANALYZED_AT"))
        verifyDatabase("fullflow/expected_species_counter.xml", specieAnalysisCounterTable, query = querySpeciesAnalysisCounter)
    }

    fun getPayloads(vararg pathsMockPayloads: String): List<JsonNode> =
        pathsMockPayloads
            .map { loadJsonFileAsJsonNode(it) }
            .flatMap { it.toList() }

    private fun performPostWithPayloadAndExpect(payload: String, resultMatcher: ResultMatcher) {
        val simianEndpoint = "/v1/simian"

        mockMvc
            .perform(
                post(simianEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
            )
            .andExpect(resultMatcher)
            .andReturn()

        Thread.sleep(200)
    }
}
