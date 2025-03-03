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
        // Crear un objeto Recording de prueba
        testRecording = new Recording();
        testRecording.setAudioData(new byte[]{1, 2, 3});
        testRecording.setRecordingDate("2023-10-15");
        testRecording.setDuration(120.0);
        testRecording.setTitle("Test Recording");
        testRecording.setPianoConfiguration("Grand Piano");

        // Asegurarse de que la base de datos esté limpia antes de cada prueba
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM recordings");
        }
    }

    @After
    public void tearDown() throws SQLException {
        // Limpiar la base de datos después de cada prueba
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM recordings");
        }
    }

    @Test
    public void testSave() {
        // Probar el método save
        RecordingRepository.save(testRecording);
        assertTrue("El ID del recording debe ser mayor que 0", testRecording.getId() > 0);
        testRecordingId = testRecording.getId();
    }

    @Test
    public void testReadAll() {
        // Probar el método readAll
        RecordingRepository.save(testRecording);
        RecordingRepository repository = new RecordingRepository();
        List<Recording> recordings = repository.readAll();
        assertNotNull("La lista de recordings no debe ser nula", recordings);
        assertFalse("La lista de recordings no debe estar vacía", recordings.isEmpty());
    }

    @Test
    public void testReadById() {
        // Probar el método readById
        RecordingRepository.save(testRecording);
        int id = testRecording.getId();
        RecordingRepository repository = new RecordingRepository();
        Recording retrievedRecording = repository.readById(id);
        assertNotNull("El recording recuperado no debe ser nulo", retrievedRecording);
        assertEquals("El título debe coincidir", testRecording.getTitle(), retrievedRecording.getTitle());
        assertEquals("La configuración del piano debe coincidir", testRecording.getPianoConfiguration(), retrievedRecording.getPianoConfiguration());
    }

    @Test
    public void testDeleteById() {
        // Probar el método deleteById
        RecordingRepository.save(testRecording);
        int id = testRecording.getId();
        RecordingRepository repository = new RecordingRepository();
        repository.deleteById(id);
        Recording deletedRecording = repository.readById(id);
        assertNull("El recording eliminado no debe existir", deletedRecording);
    }

    @Test
    public void testSaveWithNullDate() {
        // Probar guardar un recording sin fecha (debe asignar la fecha actual)
        testRecording.setRecordingDate(null);
        RecordingRepository.save(testRecording);
        assertNotNull("La fecha de grabación no debe ser nula después de guardar", testRecording.getRecordingDate());
    }

@Test
public void testSaveWithNullAudioData() {
    // Establecer audioData como null
    testRecording.setAudioData(null);
    RecordingRepository.save(testRecording);

    // Verificar que el recording tiene un ID asignado
    assertTrue("El ID del recording debe ser mayor que 0", testRecording.getId() > 0);

    // Recuperar el recording de la base de datos para verificar audioData
    RecordingRepository repository = new RecordingRepository();
    Recording retrievedRecording = repository.readById(testRecording.getId());
    assertNotNull("El recording recuperado no debe ser nulo", retrievedRecording);
    assertNull("El audioData debe ser nulo en la base de datos", retrievedRecording.getAudioData());
}


    @Test
    public void testReadByIdNonExistent() {
        // Probar leer un recording que no existe
        RecordingRepository repository = new RecordingRepository();
        Recording recording = repository.readById(-1);
        assertNull("El recording no debe existir", recording);
    }

    @Test
    public void testReadAllEmpty() {
        // Probar leer todos cuando no hay recordings
        RecordingRepository repository = new RecordingRepository();
        List<Recording> recordings = repository.readAll();
        assertNotNull("La lista no debe ser nula", recordings);
        assertTrue("La lista debe estar vacía", recordings.isEmpty());
    }
}
