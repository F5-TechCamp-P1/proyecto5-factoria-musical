package com.f5.factoria_musical.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecordingTest {

    @Test
    @DisplayName("Test initialize the recording with example values")

    public void testInitializeRecording() {
        Recording recording = new Recording();
        recording.setId(1);
        recording.setAudioData("datosAudioEjemplo".getBytes());
        recording.setRecordingDate("2021-05-05");
        recording.setDuration(120);
    }

    @Test
    @DisplayName("Test getters and setters")

    public void testGettersAndSetters() {
        Recording recording = new Recording();
        recording.setId(1);
        recording.setAudioData("datosAudioEjemplo".getBytes());
        recording.setRecordingDate("2021-05-05");
        recording.setDuration(120);

        assertEquals(1, recording.getId());
        assertEquals("datosAudioEjemplo", recording.getAudioData());
        assertEquals("2021-05-05", recording.getRecordingDate());
        assertEquals("120", recording.getDuration());
    }

    @Test
    @DisplayName("Test getDetails method")

    public void testGetDetails() {
        Recording recording = new Recording();
        recording.setId(1);
        recording.setAudioData("datosAudioEjemplo".getBytes());
        recording.setRecordingDate("2021-05-05");
        recording.setDuration(120);

        assertEquals("Recording{id=1, audioData='datosAudioEjemplo', recordingDate='2021-05-05', duration='120'}",
                recording.getDetails());
    }

    
}