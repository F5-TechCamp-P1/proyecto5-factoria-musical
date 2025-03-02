package com.f5.factoria_musical.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecordingTest {

    private Recording recording;
    private byte[] audioData;

    @BeforeEach
    public void setUp() {
        audioData = "datosAudioEjemplo".getBytes();
        recording = new Recording(1, audioData, "2021-05-05", 120.5, "Mi Grabación", "Configuración de Piano");
    }

    @Test
    @DisplayName("Test getters and setters")
    public void testGettersAndSetters() {
        Recording newRecording = new Recording();
        newRecording.setId(2);
        newRecording.setAudioData("nuevosDatosAudio".getBytes());
        newRecording.setRecordingDate("2023-10-27");
        newRecording.setDuration(180.0);
        newRecording.setTitle("Nueva Grabación");
        newRecording.setPianoConfiguration("Nueva Configuración");

        assertEquals(2, newRecording.getId());
        assertArrayEquals("nuevosDatosAudio".getBytes(), newRecording.getAudioData());
        assertEquals("2023-10-27", newRecording.getRecordingDate());
        assertEquals(180.0, newRecording.getDuration());
        assertEquals("Nueva Grabación", newRecording.getTitle());
        assertEquals("Nueva Configuración", newRecording.getPianoConfiguration());
    }

    @Test
    @DisplayName("Test getDetails method")
    public void testGetDetails() {
        String expectedDetails = "Recording{id=1, audioData='" + Base64.getEncoder().encodeToString(audioData) + '\'' +
                ", recordingDate='2021-05-05', duration=120.5, title='Mi Grabación', pianoConfiguration='Configuración de Piano'}";
        assertEquals(expectedDetails, recording.getDetails());
    }

    @Test
    @DisplayName("Test initialize the recording with all parameters constructor")
    public void testInitializeRecordingWithAllParametersConstructor() {
        assertEquals(1, recording.getId());
        assertArrayEquals(audioData, recording.getAudioData());
        assertEquals("2021-05-05", recording.getRecordingDate());
        assertEquals(120.5, recording.getDuration());
        assertEquals("Mi Grabación", recording.getTitle());
        assertEquals("Configuración de Piano", recording.getPianoConfiguration());
    }
}