package io.species.analyzer.configuration

import io.species.analyzer.domain.specie.SpecieAnalysisRepository
import org.eclipse.persistence.config.BatchWriting
import org.eclipse.persistence.config.PersistenceUnitProperties
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.jta.JtaTransactionManager
import java.util.logging.Level
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(JpaProperties::class)
@EnableJpaRepositories(basePackageClasses = [ SpecieAnalysisRepository::class ])
class DatabaseConfiguration(dataSource: DataSource,
                            jpaProperties: JpaProperties,
                            jtaTransactionManager: ObjectProvider<JtaTransactionManager>) :
    JpaBaseConfiguration(dataSource, jpaProperties, jtaTransactionManager) {

    private val TRUE = "true"
    private val FALSE = "false"

    override fun createJpaVendorAdapter(): AbstractJpaVendorAdapter = EclipseLinkJpaVendorAdapter()

    override fun getVendorProperties(): Map<String, Any> {

        val vendorProperties = HashMap<String, Any>()

        vendorProperties[PersistenceUnitProperties.WEAVING] = "static"
        vendorProperties[PersistenceUnitProperties.QUERY_CACHE] = TRUE
        vendorProperties[PersistenceUnitProperties.THROW_EXCEPTIONS] = TRUE
        vendorProperties[PersistenceUnitProperties.BATCH_WRITING] = BatchWriting.JDBC
        vendorProperties[PersistenceUnitProperties.CACHE_STATEMENTS] = FALSE
        vendorProperties[PersistenceUnitProperties.CACHE_SHARED_DEFAULT] = FALSE
        vendorProperties[PersistenceUnitProperties.PERSISTENCE_CONTEXT_FLUSH_MODE] = "commit"
        vendorProperties[PersistenceUnitProperties.PERSISTENCE_CONTEXT_CLOSE_ON_COMMIT] = TRUE
        vendorProperties[PersistenceUnitProperties.PERSISTENCE_CONTEXT_PERSIST_ON_COMMIT] = FALSE
        vendorProperties[PersistenceUnitProperties.LOGGING_LEVEL] = Level.SEVERE.name

        return vendorProperties.toMap()
    }
}
