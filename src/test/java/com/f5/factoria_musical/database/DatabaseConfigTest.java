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

    @Test
    @DisplayName("Test that invalid connection throws SQLException")
    void testInvalidConnectionThrowsSQLException() {
        assertThrows(SQLException.class, () -> {
            Connection badConnection = java.sql.DriverManager.getConnection(
                    "jdbc:h2:mem:www.sangrecontomate.mimeriend;IFEXISTS=true", "conde_dracula", "transilvaniaLoMejor");
            badConnection.close();
        }, "Connection should throw SQLException for incorrect database URL");

    }

    @Test
    @DisplayName("Ensures multiple connections can be established")
    void testMultipleConnections() throws SQLException {
        try (Connection conn1 = DatabaseConfig.getConnection();
                Connection conn2 = DatabaseConfig.getConnection()) {
            assertNotNull(conn1);
            assertNotNull(conn2);
            assertFalse(conn1.isClosed());
            assertFalse(conn2.isClosed());
        }
    }
}