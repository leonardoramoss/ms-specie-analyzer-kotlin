package io.species.analyzer.domain.specie.analyzer.manipulation

import io.species.analyzer.domain.specie.DNA
import io.species.analyzer.infrastructure.extension.diagonalMultidimensionalCharToArrayString
import io.species.analyzer.infrastructure.extension.explodeValues
import io.species.analyzer.infrastructure.extension.multidimensionalCharToStringArray
import io.species.analyzer.infrastructure.extension.reverseStringArrayValues
import io.species.analyzer.infrastructure.extension.transposeRowToColumn

class DNAManipulation(private val dna: Array<DNA>) {

    /**
     * Given an array of strings this method will transpose from row to column values
     *
     * Original    Transposed
     * A A A A     A B C D
     * B B B B     A B C D
     * C C C C     A B D D
     * D D D D     A B C D
     *
     * @param stringArray
     * @return transposed array
     */
    fun transposeDNASequence(): Array<DNA> =
        dna.explodeValues()
            .transposeRowToColumn()
            .multidimensionalCharToStringArray()

    /**
     * Given an array of strings this method will transpose diagonal from left to right for row values
     *
     * Original    Transposed
     * A B C D         A
     * B C D A        B B
     * C D A B       C C C
     * D A B C      D D D D
     *               A A A
     *                B B
     *                 C
     *
     * @param dna
     * @return transposed array
     */
    fun transposeDiagonalDNASequence(): Array<DNA> =
        dna.explodeValues()
            .diagonalMultidimensionalCharToArrayString()

    /**
     * Given an array of strings this method will transpose diagonal from right to left for row values
     *
     * Original    Transposed
     * A B C D         D
     * B C D A        A C
     * C D A B       B D B
     * D A B C      C A C A
     *               B D B
     *                A C
     *                 D
     *
     * @param dna
     * @return transposed array
     */
    fun transposeReversedDiagonalDNASequence(): Array<DNA> =
        dna.reverseStringArrayValues().explodeValues()
            .diagonalMultidimensionalCharToArrayString()
}
