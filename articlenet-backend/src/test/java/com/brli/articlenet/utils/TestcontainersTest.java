package com.brli.articlenet.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestcontainersTest extends AbstractTestcontainer {
    @Test
    void startDb() {
        Assertions.assertTrue(mysqlContainer.isCreated());
        Assertions.assertTrue(mysqlContainer.isRunning());
    }

}
