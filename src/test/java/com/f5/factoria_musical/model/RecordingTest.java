package com.f5.factoria_musical.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecordingTest {

    private Recording recording;

    @Before
    @Test
    @DisplayName("Test initialize the recording with example values")

    public void testInitializeRecording() {
        Recording recording = new Recording();
        recording.setId(1);
        recording.setAudioData("datosAudioEjemplo");
        recording.setDate("2021-05-05");
        recording.setDuration("120");
    }


}