package com.f5.factoria_musical.repository;

import com.f5.factoria_musical.model.Recording;
import com.f5.factoria_musical.database.DatabaseConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;

import java.util.List;

import static org.junit.Assert.*;

public class RecordingRepositoryTest {

    private Recording testRecording;
    @SuppressWarnings("unused")
    private int testRecordingId;

    @Before
    public void setUp() throws SQLException {
        
        testRecording = new Recording();
        testRecording.setAudioData(new byte[] { 1, 2, 3 });
        testRecording.setRecordingDate("2023-10-15");
        testRecording.setDuration(120.0);
        testRecording.setTitle("Test Recording");
        testRecording.setPianoConfiguration("Grand Piano");

        
        try (Connection connection = DatabaseConfig.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM recordings");
        }
    }

    @After
    public void tearDown() throws SQLException {
        
        try (Connection connection = DatabaseConfig.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM recordings");
        }
    }

    @Test
    public void testDeleteById() {
        
        RecordingRepository.save(testRecording);
        int id = testRecording.getId();
        RecordingRepository repository = new RecordingRepository();
        repository.deleteById(id);
        Recording deletedRecording = repository.readById(id);
        assertNull("El recording eliminado no debe existir", deletedRecording);
    }

    @Test
    public void testSaveWithNullDate() {
       
        testRecording.setRecordingDate(null);
        RecordingRepository.save(testRecording);
        assertNotNull("La fecha de grabación no debe ser nula después de guardar", testRecording.getRecordingDate());
    }

    @Test
    public void testReadByIdNonExistent() {
        
        RecordingRepository repository = new RecordingRepository();
        Recording recording = repository.readById(-1);
        assertNull("El recording no debe existir", recording);
    }

    @Test
    public void testReadAllEmpty() {
       
        RecordingRepository repository = new RecordingRepository();
        List<Recording> recordings = repository.readAll();
        assertNotNull("La lista no debe ser nula", recordings);
        assertTrue("La lista debe estar vacía", recordings.isEmpty());
    }
}
