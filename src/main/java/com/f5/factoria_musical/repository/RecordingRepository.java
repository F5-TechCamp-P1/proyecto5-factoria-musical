package com.f5.factoria_musical.repository;

import com.f5.factoria_musical.model.Recording;
import com.f5.factoria_musical.database.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordingRepository {

    public static void save(Recording recording) {

        String sql = "INSERT INTO recordings (audio_data, recording_date, duration) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBytes(1, recording.getAudioData());
            ps.setDate(2, Date.valueOf(recording.getRecordingDate()));
            ps.setInt(3, recording.getDuration());

            ps.executeUpdate();

            // Obtener el ID generado automáticamente por la base de datos
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recording.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
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
                recording.setRecordingDate(resultSet.getString("recording_date").toString());
                recording.setDuration(resultSet.getInt("duration"));
                recordings.add(recording);
            }
        } catch (SQLException e) {
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
                    recording.setRecordingDate(resultSet.getString("recording_date").toString());
                    recording.setDuration(resultSet.getInt("duration"));
                }
            }
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }

}
