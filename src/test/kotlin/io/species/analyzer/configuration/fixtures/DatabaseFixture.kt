package io.species.analyzer.configuration.fixtures

import org.dbunit.Assertion.assertEqualsByQuery
import org.dbunit.DatabaseUnitException
import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.database.QueryDataSet
import org.dbunit.dataset.DataSetException
import org.dbunit.dataset.ReplacementDataSet
import org.dbunit.dataset.xml.FlatXmlDataSet
import org.dbunit.dataset.xml.FlatXmlProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.xml.sax.InputSource
import java.io.FileOutputStream
import java.lang.Exception
import java.sql.SQLException

@Component
class DatabaseFixture(@Autowired private val jdbcTemplate: JdbcTemplate) : DatabaseAssertion {

    private var databaseConnection: IDatabaseConnection? = null

    override fun verifyDatabase(fileName: String, tableName: String, query: String?, vararg ignoredColumns: String) =
        assertEqualsByQuery(loadDataSet("/expected/datasets/$fileName"), databaseConnection(), query, tableName, ignoredColumns)

    private fun databaseConnection() : IDatabaseConnection =
        try {
            databaseConnection = databaseConnection?: DatabaseConnection(jdbcTemplate.dataSource?.connection)
            databaseConnection as IDatabaseConnection
        } catch (e: DatabaseUnitException) {
            throw RuntimeException(e.cause)
        } catch (e: SQLException) {
            throw RuntimeException(e.cause)
        }

    private fun loadDataSet(datasetFile: String) =
        try {
            val source = InputSource(javaClass.getResourceAsStream(datasetFile))
            val producer = FlatXmlProducer(source, false, true)
            val dataSet = ReplacementDataSet(FlatXmlDataSet(producer))

            dataSet.addReplacementObject("[NULL]", null)

            dataSet
        } catch (e: DataSetException) {
            throw RuntimeException("Cannot read the dataset file $datasetFile", e)
        }

    fun databaseExpectationBuilder() : DatabaseExpectationBuilder = DatabaseExpectationBuilder()

    inner class DatabaseExpectationBuilder(private var mappedQueries: MutableMap<String, MutableList<String>> = mutableMapOf()) {

        fun addExpectation(tableName: String) : DatabaseExpectationBuilder =
            addExpectation(tableName, "SELECT * FROM $tableName")

        fun addExpectation(tableName: String, query: String): DatabaseExpectationBuilder {
            if (mappedQueries.containsKey(tableName)) {
                mappedQueries[tableName]?.add(query)
            } else {
                mappedQueries[tableName] = mutableListOf(query)
            }
            return this
        }

        @Throws(Exception::class)
        fun write(fileName: String?) {
            val queryDataSet = QueryDataSet(databaseConnection())
            for ((key, value) in mappedQueries) {
                for (query in value) {
                    queryDataSet.addTable(key, query)
                }
            }
            FlatXmlDataSet.write(queryDataSet, FileOutputStream("src/test/resources/expected/datasets/$fileName"))
        }
    }
}
