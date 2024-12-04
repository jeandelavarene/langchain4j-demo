package com.example.demo.configuration.store.oracle;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.oracle.CreateOption;
import dev.langchain4j.store.embedding.oracle.OracleEmbeddingStore;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("oracle")
public class OracleEmbeddingStoreConfiguration {

    @Bean
    EmbeddingStore<TextSegment> embeddingStore() throws SQLException {

        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setConnectionFactoryClassName(
                "oracle.jdbc.datasource.impl.OracleDataSource");
        dataSource.setURL("jdbc:oracle:thin:@config-file://jdbc_connection_properties.json");

        return OracleEmbeddingStore.builder()
                .dataSource(dataSource)
                .embeddingTable("profile_oracle",
                        CreateOption.CREATE_OR_REPLACE)
                .build();

    }
}