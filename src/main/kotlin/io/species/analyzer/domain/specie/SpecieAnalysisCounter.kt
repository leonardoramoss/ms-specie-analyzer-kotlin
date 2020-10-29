package io.species.analyzer.domain.specie

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "SPECIE", name = "SPECIES_ANALYSIS_COUNTER")
data class SpecieAnalysisCounter(

    @Id
    @Column(name = "UUID")
    val uuid: UUID? = null,

    @Column(name = "SPECIE")
    @Enumerated(EnumType.STRING)
    val specieIdentifier: SpecieIdentifier?,

    @Column(name = "COUNTER")
    val counter: Long? = 1
) {
    protected constructor() : this(null, null, null)
}
