package com.f5.factoria_musical.repository;

import com.f5.factoria_musical.model.Recording;
import com.f5.factoria_musical.database.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordingRepository {

    public static void save(Recording recording) {

        String sql = "INSERT INTO recordings (id,audio_data, date, duration) VALUES (?, ?, ?,?)";
        try (Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, recording.getId());
            ps.setString(2, recording.getAudioData());
            ps.setDate(3, Date.valueOf(recording.getDate()));
            ps.setInt(4, recording.getDuration());

            ps.executeUpdate();
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
                recording.setAudioData(resultSet.getString("audio_data"));
                recording.setDate(resultSet.getString("date").toString());
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
                    recording.setAudioData(resultSet.getString("audio_data"));
                    recording.setDate(resultSet.getString("date").toString());
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
