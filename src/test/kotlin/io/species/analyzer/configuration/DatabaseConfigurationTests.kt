package io.species.analyzer.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
class DatabaseConfigurationTests {

    @Bean
    @ConditionalOnMissingBean(type = ["dataSource"])
    fun dataSource(): DataSource =
        EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build()
}
