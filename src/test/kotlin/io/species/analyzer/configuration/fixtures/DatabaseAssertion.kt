package io.species.analyzer.configuration.fixtures

interface DatabaseAssertion {

    fun verifyDatabase(fileName: String, tableName: String, query: String? = "SELECT * FROM $tableName", vararg ignoredColumns: String)
}
