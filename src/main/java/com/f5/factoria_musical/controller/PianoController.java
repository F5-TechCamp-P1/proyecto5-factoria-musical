package com.f5.factoria_musical.controller;

import java.sql.*;

public class PianoController {
    private static final String JDBC_URL = "jdbc:h2:./data/mydb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public PianoController() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Piano (id INT PRIMARY KEY AUTO_INCREMENT, volume FLOAT, soundType VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String updatePiano(float volume, String soundType) {
        if (!isValidSoundType(soundType)) {
            return "Invalid sound type! Use: Classical, Organ, Electric.";
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM Piano LIMIT 1");
            if (rs.next()) {
                stmt.executeUpdate("UPDATE Piano SET volume=" + volume + ", soundType='" + soundType + "' WHERE id=" + rs.getInt("id"));
                return "Piano updated: Volume=" + volume + ", SoundType=" + soundType;
            } else {
                stmt.executeUpdate("INSERT INTO Piano (volume, soundType) VALUES (" + volume + ", '" + soundType + "')");
                return "Piano created: Volume=" + volume + ", SoundType=" + soundType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating piano.";
        }
    }

    public String getPiano() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Piano LIMIT 1")) {

            if (rs.next()) {
                return "Piano{id=" + rs.getInt("id") + ", volume=" + rs.getFloat("volume") + ", soundType='" + rs.getString("soundType") + "'}";
            } else {
                return "No piano found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving piano.";
        }
    }

    private boolean isValidSoundType(String soundType) {
        return soundType.equals("Classical") || soundType.equals("Organ") || soundType.equals("Electric");
    }
}
