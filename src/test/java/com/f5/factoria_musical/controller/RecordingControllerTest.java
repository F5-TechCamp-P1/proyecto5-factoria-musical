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
       
        recordingRepositoryMock = mock(RecordingRepository.class);

      
        recordingController = new RecordingController();

       
        Field repositoryField = RecordingController.class.getDeclaredField("recordingRepository");
        repositoryField.setAccessible(true); 
        repositoryField.set(recordingController, recordingRepositoryMock); 

    
        httpExchangeMock = mock(HttpExchange.class);

      
        Headers headers = new Headers();
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headers);

    
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
       
        when(recordingRepositoryMock.readAll()).thenReturn(Arrays.asList(testRecording));

       
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

      
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

      
        recordingController.handle(httpExchangeMock);

       
        verify(recordingRepositoryMock, times(1)).readAll();

       
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(200, (int) statusCodeCaptor.getValue());

        
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
        
        when(recordingRepositoryMock.readById(1)).thenReturn(testRecording);

      
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/1"));

       
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

     
        recordingController.handle(httpExchangeMock);

        verify(recordingRepositoryMock, times(1)).readById(1);

      
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(200, (int) statusCodeCaptor.getValue());

    
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
   
        when(recordingRepositoryMock.readById(999)).thenReturn(null);

       
        when(httpExchangeMock.getRequestMethod()).thenReturn("GET");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/999"));

        
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

    
        recordingController.handle(httpExchangeMock);

        verify(recordingRepositoryMock, times(1)).readById(999);

        
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(404, (int) statusCodeCaptor.getValue());

       
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Recording not found.\"}", response);
    }

    @Test
    public void testHandleDeleteRecordingNotFound() throws Exception {
        
        when(recordingRepositoryMock.readById(999)).thenReturn(null);

     
        when(httpExchangeMock.getRequestMethod()).thenReturn("DELETE");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings/999"));

        
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

     
        recordingController.handle(httpExchangeMock);

        
        verify(recordingRepositoryMock, never()).deleteById(anyInt());

      
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> responseLengthCaptor = ArgumentCaptor.forClass(Long.class);

        verify(httpExchangeMock).sendResponseHeaders(statusCodeCaptor.capture(), responseLengthCaptor.capture());
        assertEquals(404, (int) statusCodeCaptor.getValue());

        
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Recording not found for deletion.\"}", response);
    }

    @Test
    public void testHandleOptions() throws Exception {
        
        when(httpExchangeMock.getRequestMethod()).thenReturn("OPTIONS");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

        
        OutputStream responseBody = mock(OutputStream.class);
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

      
        recordingController.handle(httpExchangeMock);

        
        verify(httpExchangeMock).sendResponseHeaders(204, -1);

        
        Headers responseHeaders = httpExchangeMock.getResponseHeaders();
        assertTrue(responseHeaders.containsKey("Access-Control-Allow-Methods"));
        assertTrue(responseHeaders.containsKey("Access-Control-Allow-Headers"));

       
        verify(responseBody, times(1)).close();
    }

    @SuppressWarnings("unused")
    @Test
    public void testHandleInvalidMethod() throws Exception {
      
        when(httpExchangeMock.getRequestMethod()).thenReturn("PUT");
        when(httpExchangeMock.getRequestURI()).thenReturn(new java.net.URI("/recordings"));

      
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseBody);

      
        recordingController.handle(httpExchangeMock);

     
        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> responseLengthCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(httpExchangeMock).sendResponseHeaders(eq(405),
                eq((long) "{\"error\":\"Method Not Allowed\"}".getBytes().length));
        assertEquals(405, (int) 405);

      
        String response = responseBody.toString(StandardCharsets.UTF_8.name());
        assertEquals("{\"error\":\"Method Not Allowed\"}", response);
    }
}
