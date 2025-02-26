package com.f5.factoria_musical.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PianoControllerTest {

    private PianoController controller;

    @BeforeEach
    void setUp() {
        controller = new PianoController();
    }

    @Test
    void testGetPiano() {
    controller.updatePiano(80.0f, "Organ");
    assertTrue(controller.getPiano().contains("Organ"));
    }

    @Test
    void testUpdatePiano() {
    assertTrue(controller.updatePiano(70.0f, "Classical").contains("Piano"));
    }
}
