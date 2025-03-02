package com.f5.factoria_musical.repository;

import com.f5.factoria_musical.model.Recording;
import com.f5.factoria_musical.database.DatabaseConfig;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordingRepository {

    public static void save(Recording recording) {

        if (recording.getRecordingDate() == null || recording.getRecordingDate().isEmpty()) {
            recording.setRecordingDate(LocalDate.now().toString());
        }

        String sql = "INSERT INTO recordings (audio_data, recording_date, duration, title, piano_configuration) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBytes(1, recording.getAudioData());
            ps.setDate(2, Date.valueOf(recording.getRecordingDate()));
            ps.setDouble(3, recording.getDuration());
            ps.setString(4, recording.getTitle());
            ps.setString(5, recording.getPianoConfiguration());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recording.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to obtain ID after insertion.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Recording> readAll() {
        List<Recording> recordings = new ArrayList<>();
        String sql = "SELECT * FROM recordings";

        try (Connection connection = DatabaseConfig.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Recording recording = new Recording();
                recording.setId(resultSet.getInt("id"));
                recording.setAudioData(resultSet.getBytes("audio_data"));
                recording.setRecordingDate(resultSet.getString("recording_date"));
                recording.setDuration( resultSet.getDouble("duration"));
                recording.setTitle(resultSet.getString("title"));
                recording.setPianoConfiguration(resultSet.getString("piano_configuration"));
                recordings.add(recording);
            }
        } catch (SQLException e) {
            System.err.println("Error reading recordings: " + e.getMessage());
            e.printStackTrace();
        }
        return recordings;
    }

    public Recording readById(int id) {
        String sql = "SELECT * FROM recordings WHERE id = ?";
        Recording recording = null;

        try (Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    recording = new Recording();
                    recording.setId(resultSet.getInt("id"));
                    recording.setAudioData(resultSet.getBytes("audio_data"));
                    recording.setRecordingDate(resultSet.getString("recording_date"));
                    recording.setDuration(resultSet.getInt("duration"));
                    recording.setTitle(resultSet.getString("title"));
                    recording.setPianoConfiguration(resultSet.getString("piano_configuration"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading recording by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return recording;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM recordings WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting recording: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
