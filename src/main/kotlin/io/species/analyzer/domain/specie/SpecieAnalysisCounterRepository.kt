package io.species.analyzer.domain.specie

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

interface SpecieAnalysisCounterRepository : JpaRepository<SpecieAnalysisCounter, UUID> {

    fun findAllBySpecieIdentifierIn(identifiers: List<SpecieIdentifier>): List<SpecieAnalysisCounter>?

    @Modifying
    @Transactional
    @Query(value = "UPDATE SpecieAnalysisCounter s SET s.counter = s.counter + 1 WHERE s.specieIdentifier = ?1")
    fun incrementSpecieCounter(speciesIdentifier: SpecieIdentifier): Int
}
