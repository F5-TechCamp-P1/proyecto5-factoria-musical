package com.f5.factoria_musical.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PianoTest {
    @Test
    void testGetConfiguration() {
Piano piano = new Piano(1, 50.0f, "Grand");
        assertEquals("Default", piano.getConfiguration(), "Default configuration should be 'Default'");
    }

    @Test
    void testGetId() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        assertEquals(1, piano.getId(), "ID should be 1");
    }

    @Test
    void testGetSoundType() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        assertEquals("Grand", piano.getSoundType(), "Sound type should be 'Grand'");
    }

    @Test
    void testGetVolume() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        assertEquals(50.0f, piano.getVolume(), "Volume should be 50.0");
    }

    @Test
    void testSetConfiguration() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        piano.setConfiguration("Custom Reverb");
        assertEquals("Custom Reverb", piano.getConfiguration(), "Configuration should be updated to 'Custom Reverb'");
    }

    @Test
    void testSetId() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        piano.setId(2);
        assertEquals(2, piano.getId(), "ID should be updated to 2");
    }

    @Test
    void testSetSoundType() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        piano.setSoundType("Electric");
        assertEquals("Electric", piano.getSoundType(), "Sound type should be updated to 'Electric'");
    }

    @Test
    void testSetVolume() {
        Piano piano = new Piano(1, 50.0f, "Grand");
        piano.setVolume(75.5f);
        assertEquals(75.5f, piano.getVolume(), "Volume should be updated to 75.5");
    }

    @Test
    void testToString() {
        Piano piano = new Piano(3, 60.0f, "Acoustic");
        String expectedString = "Piano{id=3, volume=60.0, soundType='Acoustic', configuration='Default'}";
        assertEquals(expectedString, piano.toString(), "toString() output is incorrect");
    }
}
