package com.brli.articlenet.utils;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestcontainer {

    protected AbstractTestcontainer() {
        System.out.println("Creating test containers");
    }

    @Container
    protected static final MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:latest")
                    .withDatabaseName("article_net")
                    .withUsername("root")
                    .withPassword("root");

    @BeforeAll
    static void beforeAll() {
        String dataSource = mysqlContainer.getJdbcUrl();
        String username = mysqlContainer.getUsername();
        String password = mysqlContainer.getPassword();
        Flyway flyway = Flyway.configure().dataSource(
                dataSource, username,password
        ).load();
        flyway.migrate();
    }

    @AfterAll
    static void afterAll() {
        mysqlContainer.stop();
    }

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add( "spring.datasource.url", mysqlContainer::getJdbcUrl );
        registry.add( "spring.datasource.username", mysqlContainer::getUsername );
        registry.add( "spring.datasource.password", mysqlContainer::getPassword );
    }

}
