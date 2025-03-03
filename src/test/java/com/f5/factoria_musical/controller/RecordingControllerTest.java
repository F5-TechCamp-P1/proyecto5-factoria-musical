package com.f5.factoria_musical.controller;

import com.f5.factoria_musical.model.Recording;
import com.f5.factoria_musical.repository.RecordingRepository;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecordingControllerTest {

    private RecordingController recordingController;
    private RecordingRepository recordingRepositoryMock;
    private HttpExchange httpExchangeMock;
    private Recording testRecording;

    @Before
    public void setUp() throws Exception {
        // Crear un mock del RecordingRepository
        recordingRepositoryMock = mock(RecordingRepository.class);

        // Crear una instancia del controlador
        recordingController = new RecordingController();

        // Usar reflexión para acceder y reemplazar el campo privado recordingRepository
        Field repositoryField = RecordingController.class.getDeclaredField("recordingRepository");
        repositoryField.setAccessible(true); // Hacer el campo accesible
        repositoryField.set(recordingController, recordingRepositoryMock); // Asignar el mock al campo privado

        // Crear un mock de HttpExchange
        httpExchangeMock = mock(HttpExchange.class);

        // Configurar Headers
        Headers headers = new Headers();
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headers);

        // Crear un objeto Recording de prueba
        testRecording = new Recording();
        testRecording.setId(1);
        testRecording.setAudioData(new byte[] { 1, 2, 3 });
        testRecording.setRecordingDate("2023-10-15");
        testRecording.setDuration(120.0);
        testRecording.setTitle("Test Recording");
        testRecording.setPianoConfiguration("Grand Piano");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHandleGetAllRecordings() throws Exception {
        // Configurar el repositorio para devolver una lista con el recording de prueba
        when(recordingRepositoryMock.readAll()).thenReturn(Arrays.asList(testRecording));

        // Configurar el HttpExchange para una solicitud GET a "/recordings"
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

        // Capturar el OutputStream de la respuesta
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar que readAll fue llamado
        verify(recordingRepositoryMock, times(1)).readAll();

        // Verificar el código de respuesta y el cuerpo
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(200, (int) statusCodeCaptor.getValue());

        // Verificar el contenido de la respuesta
        
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        JSONArray expectedArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", testRecording.getId());
        jsonObject.put("audioData", Base64.getEncoder().encodeToString(testRecording.getAudioData()));
        jsonObject.put("recordingDate", testRecording.getRecordingDate());
        jsonObject.put("duration", testRecording.getDuration());
        jsonObject.put("title", testRecording.getTitle());
        jsonObject.put("pianoConfiguration", testRecording.getPianoConfiguration());
        expectedArray.add(jsonObject);
        assertEquals(expectedArray.toJSONString(), response);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHandleGetRecordingById() throws Exception {
        // Configurar el repositorio para devolver el recording de prueba
        when(recordingRepositoryMock.readById(1)).thenReturn(testRecording);

        // Configurar el HttpExchange para una solicitud GET a "/recordings/1"
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/1"));

        // Capturar el OutputStream de la respuesta
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar que readById fue llamado con el ID correcto
        verify(recordingRepositoryMock, times(1)).readById(1);

        // Verificar el código de respuesta y el cuerpo
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(200, (int) statusCodeCaptor.getValue());

        // Verificar el contenido de la respuesta
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        JSONObject expectedObject = new JSONObject();
        expectedObject.put("id", testRecording.getId());
        expectedObject.put("audioData", Base64.getEncoder().encodeToString(testRecording.getAudioData()));
        expectedObject.put("recordingDate", testRecording.getRecordingDate());
        expectedObject.put("duration", testRecording.getDuration());
        expectedObject.put("title", testRecording.getTitle());
        expectedObject.put("pianoConfiguration", testRecording.getPianoConfiguration());
        assertEquals(expectedObject.toJSONString(), response);
    }

    @Test
    public void testHandleGetRecordingByIdNotFound() throws Exception {
        // Configurar el repositorio para devolver null cuando el ID no existe
        when(recordingRepositoryMock.readById(999)).thenReturn(null);

        // Configurar el HttpExchange para una solicitud GET a "/recordings/999"
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/999"));

        // Capturar el OutputStream de la respuesta
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar que readById fue llamado con el ID correcto
        verify(recordingRepositoryMock, times(1)).readById(999);

        // Verificar el código de respuesta y el cuerpo
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(404, (int) statusCodeCaptor.getValue());

        // Verificar el contenido de la respuesta
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Recording not found.\"}", response);
    }

    @Test
    public void testHandleDeleteRecordingNotFound() throws Exception {
        // Configurar el repositorio para que readById devuelva null
        when(recordingRepositoryMock.readById(999)).thenReturn(null);

        // Configurar el HttpExchange para una solicitud DELETE a "/recordings/999"
        when(httpExchangeMock.getRequestMethod()).thenReturn("DELETE");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/999"));

        // Capturar el OutputStream de la respuesta
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar que deleteById no fue llamado
        verify(recordingRepositoryMock, never()).deleteById(anyInt());

        // Verificar el código de respuesta
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(404, (int) statusCodeCaptor.getValue());

        // Verificar el contenido de la respuesta
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Recording not found for deletion.\"}", response);
    }

    @Test
    public void testHandleOptions() throws Exception {
        // Configurar el HttpExchange para una solicitud OPTIONS a "/recordings"
        when(httpExchangeMock.getRequestMethod()).thenReturn("OPTIONS");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

        // Capturar el OutputStream de la respuesta
        OutputStream responseBody = mock(OutputStream.class);
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar el código de respuesta
        verify(httpExchangeMock).sendResponseHeaders(204, -1);

        // Verificar los headers de la respuesta
        Headers responseHeaders = httpExchangeMock.getResponseHeaders();
        assertTrue(responseHeaders.containsKey("Access-Control-Allow-Methods"));
        assertTrue(responseHeaders.containsKey("Access-Control-Allow-Headers"));

        // Verificar que el cuerpo de la respuesta está cerrado
        verify(responseBody, times(1)).close();
    }

    @SuppressWarnings("unused")
    @Test
    public void testHandleInvalidMethod() throws Exception {
        // Configurar el HttpExchange para una solicitud con un método no soportado
        when(httpExchangeMock.getRequestMethod()).thenReturn("PUT");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

        // Capturar el OutputStream de la respuesta
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

        // Llamar al método handle
        recordingController.handle(httpExchangeMock);

        // Verificar el código de respuesta y el contenido
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> responseLengthCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(httpExchangeMock).sendResponseHeaders(eq(405),
                eq((long) "{\"error\":\"Method Not Allowed\"}".getBytes().length));
        assertEquals(405, (int) 405);

        // Verificar el contenido de la respuesta
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Method Not Allowed\"}", response);
    }
}
