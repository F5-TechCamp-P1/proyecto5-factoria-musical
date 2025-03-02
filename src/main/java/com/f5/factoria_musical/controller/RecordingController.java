package com.f5.factoria_musical.controller;

import com.f5.factoria_musical.repository.RecordingRepository;
import com.f5.factoria_musical.model.Recording;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class RecordingController implements HttpHandler {

    private final RecordingRepository recordingRepository;

    public RecordingController() {
        this.recordingRepository = new RecordingRepository();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String method = exchange.getRequestMethod().toUpperCase();

        try {
            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                case "OPTIONS":
                    handleOptions(exchange);
                    break;
                default:
                    sendResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"error\":\"Internal server error: " + e.getMessage() + "\"}");
        }
    }


    private void handleOptions(HttpExchange exchange) throws IOException {
    
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        exchange.sendResponseHeaders(204, -1);
        exchange.getResponseBody().close();
        return;
    }
    
    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath(); 
        String[] segments = path.split("/");
        if (segments.length >= 3) { 
            try {
                int id = Integer.parseInt(segments[2]);
                Recording rec = recordingRepository.readById(id);
                if (rec != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", rec.getId());
                    jsonObject.put("audioData", Base64.getEncoder().encodeToString(rec.getAudioData()));
                    jsonObject.put("recordingDate", rec.getRecordingDate());
                    jsonObject.put("duration", rec.getDuration());
                    jsonObject.put("title", rec.getTitle());
                    jsonObject.put("pianoConfiguration", rec.getPianoConfiguration());
                    sendResponse(exchange, 200, jsonObject.toJSONString());
                } else {
                    sendResponse(exchange, 404, "{\"error\":\"Recording not found.\"}");
                }
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "{\"error\":\"Invalid ID format.\"}");
            }
        } else {
            List<Recording> recordings = recordingRepository.readAll();
            JSONArray jsonArray = new JSONArray();
            for (Recording r : recordings) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", r.getId());
                jsonObject.put("audioData", Base64.getEncoder().encodeToString(r.getAudioData()));
                jsonObject.put("recordingDate", r.getRecordingDate());
                jsonObject.put("duration", r.getDuration());
                jsonObject.put("title", r.getTitle());
                jsonObject.put("pianoConfiguration", r.getPianoConfiguration());
                jsonArray.add(jsonObject);
            }
            sendResponse(exchange, 200, jsonArray.toJSONString());
        }
    }

  
    
    private void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(body);
            String audioDataStr = json.get("audioData").toString();
            byte[] audioData = Base64.getDecoder().decode(audioDataStr);
            double duration = ((Number) json.get("duration")).doubleValue();
            String title = json.get("title").toString();
            String pianoConfiguration = json.get("pianoConfiguration").toString();
        
            Recording recording = new Recording(0, audioData, null, duration, title, pianoConfiguration);
            RecordingRepository.save(recording);
            sendResponse(exchange, 201, "{\"message\":\"Recording saved successfully\"}");
        } catch (ParseException e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"error\":\"Invalid JSON format.\"}");
        }
    }

    
    private void handleDelete(HttpExchange exchange) throws IOException {
        
        String path = exchange.getRequestURI().getPath();
        String[] segments = path.split("/");
        if (segments.length >= 3) {
            try {
                int id = Integer.parseInt(segments[2]);
                
                Recording rec = recordingRepository.readById(id);
                if (rec == null) {
                    sendResponse(exchange, 404, "{\"error\":\"Recording not found for deletion.\"}");
                } else {
                    recordingRepository.deleteById(id);
                    sendResponse(exchange, 200, "{\"message\":\"Recording deleted successfully\"}");
                }
            } catch (NumberFormatException e) {
                sendResponse(exchange, 400, "{\"error\":\"Invalid ID format.\"}");
            }
        } else {
            sendResponse(exchange, 400, "{\"error\":\"Missing ID in URL.\"}");
        }
    }
    

    
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
