package com.f5.factoria_musical.database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import java.sql.Connection;
import java.sql.SQLException;


class DatabaseConfigTest {
    private Connection connection;

    @BeforeEach
    @DisplayName("Establish connection before each test")
    void setUp() throws SQLException {
        connection = DatabaseConfig.getConnection();
    }

    @AfterEach
    @DisplayName("Close connection after each test")
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    @DisplayName("Test connection is established")
    void testConnectionIsEstablished() {
        assertNotNull(connection, "Connection should not be null");
    }

    @Test
    @DisplayName("Test connection is valid")
    void testConnectionIsValid() throws SQLException {
        assertTrue(connection.isValid(2), "Connection should be valid");
    }
}
