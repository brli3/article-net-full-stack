package com.brli.articlenet.utils;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@Slf4j
public abstract class AbstractTestcontainer {

    protected AbstractTestcontainer() {
        log.info("AbstractTestContainer constructor called");
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("article_net")
            .withUsername("brli3")
            .withPassword("password");

    static {
        postgreSQLContainer.start();
        log.info("DB container started ...");
        migrateDatabase();
        log.info("DB migrated ...");
    }

    private static void migrateDatabase() {
        String dataSource = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();
        Flyway flyway = Flyway.configure().dataSource(
                dataSource, username, password
        ).load();
        flyway.migrate();
    }

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        System.out.println("Data source configured dynamically from container");
        registry.add( "spring.datasource.url", postgreSQLContainer::getJdbcUrl );
        registry.add( "spring.datasource.username", postgreSQLContainer::getUsername );
        registry.add( "spring.datasource.password", postgreSQLContainer::getPassword );
    }
    private static DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

}

