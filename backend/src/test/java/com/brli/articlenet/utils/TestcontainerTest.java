package com.brli.articlenet.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestcontainerTest extends AbstractTestcontainer {
    @Test
    void canStartPostgresDB() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

}
