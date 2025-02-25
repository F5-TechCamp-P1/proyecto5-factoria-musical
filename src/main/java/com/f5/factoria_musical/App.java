package com.f5.factoria_musical;

import java.sql.Connection;
import java.sql.SQLException;

import com.f5.factoria_musical.database.DatabaseConfig;

public final class App {
    private App() {}

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConfig.getConnection();
            System.out.println("Connected to H2 database successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}