package com.f5.factoria_musical.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS recordings (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "audio_data BLOB," +
                "recording_date DATE," +
                "duration DOUBLE," +
                "title VARCHAR(255)," +
                "piano_configuration VARCHAR(255));";

        try (Connection connection = DatabaseConfig.getConnection();
                Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Tabla 'recordings' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'recordings': " + e.getMessage());
            e.printStackTrace();
        }
    }
}
