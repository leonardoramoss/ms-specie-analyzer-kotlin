package io.species.analyzer.domain.specie

import io.species.analyzer.infrastructure.exception.SpecieAnalysisValidationException
import io.species.analyzer.infrastructure.generator.UUIDGenerator
import java.time.LocalDateTime
import java.util.UUID
import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

typealias DNA = String

@Entity
@Table(schema = "SPECIE", name = "SPECIES_ANALYSIS")
data class SpecieAnalysis(
    @Id
    @Column(name = "UUID")
    val uuid: UUID? = null,

    @Column(name = "DNA")
    val dna: DNA,

    @Transient
    var expectedIdentifier: SpecieIdentifier? = null,

    @Column(name = "SPECIE")
    @Enumerated(EnumType.STRING)
    var identifier: SpecieIdentifier? = SpecieIdentifier.NOT_IDENTIFIED,

    @Column(name = "ANALYZED_AT")
    var analyzedAt: LocalDateTime? = null
) {
    protected constructor() : this(dna = "")

    companion object {

        @JvmField val ALLOWED_NITROGENOUS_BASE: Pattern = Pattern.compile("[ATCG]+")

        private const val MINIMUM_NxN_LENGTH = 4
        const val DELIMITER = "-"

        private operator fun invoke(uuid: UUID?, dna: DNA, expectedIdentifier: SpecieIdentifier?, identifier: SpecieIdentifier?, analyzedAt: LocalDateTime?): SpecieAnalysis =
            SpecieAnalysis(uuid, dna, expectedIdentifier ?: identifier, identifier, analyzedAt)

        operator fun invoke(uuid: UUID? = null, dna: Array<DNA>? = null, expectedIdentifier: SpecieIdentifier? = null, identifier: SpecieIdentifier? = null, analyzedAt: LocalDateTime? = null): SpecieAnalysis {
            dna?.let {
                if (dna.isNullOrEmpty().not() && it.size >= MINIMUM_NxN_LENGTH) {
                    checkAllowedNitrogenousBase(it)
                    checkDNAStructure(it)
                    return invoke(uuid, it.joinToString(DELIMITER), expectedIdentifier, identifier, analyzedAt)
                }
            }

            throw SpecieAnalysisValidationException("DNA sequence ${dna?.joinToString() ?: "null"} is invalid.")
        }

        private fun checkAllowedNitrogenousBase(dna: Array<DNA>) {
            dna.forEach {
                if (ALLOWED_NITROGENOUS_BASE.matcher(it).matches().not())
                    throw SpecieAnalysisValidationException("DNA sequence $it in ${dna.joinToString()} is not valid.")
            }
        }

        private fun checkDNAStructure(dna: Array<DNA>) {
            val sequenceLength = dna.size
            dna.forEach {
                if (sequenceLength != it.length)
                    throw SpecieAnalysisValidationException("There is not a NxN DNA sequence.")
            }
        }
    }

    /**
     *
     */
    fun originalDNA(): Array<DNA> =
        this.dna.split(DELIMITER).toTypedArray()

    /**
     *
     */
    fun withUUID(uuidGenerator: UUIDGenerator<SpecieAnalysis>): SpecieAnalysis =
        this.withUUID(uuidGenerator.generate(this))

    /**
     *
     */
    private fun withUUID(uuid: UUID): SpecieAnalysis =
        invoke(uuid, this.dna, this.expectedIdentifier, this.identifier, this.analyzedAt)

    /**
     *
     */
    fun markExpectedIdentifierAs(specieIdentifier: SpecieIdentifier): SpecieAnalysis {
        this.expectedIdentifier = specieIdentifier
        return this
    }

    /**
     *
     */
    fun markIdentifiedAs(specieIdentifier: SpecieIdentifier): SpecieAnalysis {
        this.identifier = specieIdentifier
        this.analyzedAt = LocalDateTime.now()
        return this
    }

    /**
     *
     */
    fun isIdentifierMatchesAsExpected(): Boolean {
        if (this.uuid != null && this.analyzedAt != null &&
            this.expectedIdentifier == null && this.identifier != null
        ) {
            return true
        }
        if (this.identifier == null) {
            return false
        }
        return expectedIdentifier == identifier
    }
}
