package io.species.analyzer.presentation.rest

import io.species.analyzer.application.SpecieAnalyzerApplicationService
import io.species.analyzer.configuration.fixtures.DatabaseAssertion
import io.species.analyzer.configuration.fixtures.DatabaseFixture
import io.species.analyzer.configuration.fixtures.JsonFixture.Companion.loadJsonFile
import io.species.analyzer.domain.specie.stats.StatsIdentifier
import io.species.analyzer.infrastructure.exception.StatsExecutorException
import org.junit.FixMethodOrder
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.runners.MethodSorters
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.JVM)
internal class SpecieAnalysisStatsIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val databaseFixture: DatabaseFixture,
    @Autowired private val specieAnalyzerApplicationService: SpecieAnalyzerApplicationService
) : DatabaseAssertion by databaseFixture {

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql", "classpath:scripts/data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenPreviousSpeciesAnalyzed_whenPerformGetStats_shouldBeReturnRatioStatsAndStatusOk() {
        val mvcResult = performGetAndExpect(status().isOk)
        assertEquals(loadJsonFile("expected/response/stats/expected_stats.json"), mvcResult.response.contentAsString, true)
    }

    @Test
    @SqlGroup(
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql(scripts = ["classpath:scripts/clear.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    )
    fun givenNoneSpeciesAnalyzed_whenPerformGetStats_shouldBeReturnRatioZeroStatsAndStatusOk() {
        val mvcResult = performGetAndExpect(status().isOk)
        assertEquals(loadJsonFile("expected/response/stats/expected_zero_stats.json"), mvcResult.response.contentAsString, true)
    }

    @Test
    fun givenANotIdentifierStats_whenPerformViewStats_shouldBeThrowsStatsExecutorException() {
        assertThrows(StatsExecutorException::class.java) { specieAnalyzerApplicationService.viewStats(StatsIdentifier.NOT_IDENTIFIED) }
    }

    protected fun performGetAndExpect(expected: ResultMatcher): MvcResult =
        try {
            val statsEndpoint = "/v1/stats"
            mockMvc.perform(MockMvcRequestBuilders.get(statsEndpoint)).andExpect(expected).andReturn()
        } catch (exception: Exception) {
            throw RuntimeException(exception)
        }
}
