package io.species.analyzer.domain.specie

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpecieAnalysisRepository : JpaRepository<SpecieAnalysis, UUID>
