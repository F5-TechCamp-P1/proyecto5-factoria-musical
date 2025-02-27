package com.f5.factoria_musical.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.f5.factoria_musical.repository.RecordingRepository;
import com.f5.factoria_musical.model.Recording;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")
public class RecordingController implements HttpHandler {
    private RecordingRepository recordingRepository;

    public RecordingController() {
        this.recordingRepository = new RecordingRepository();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configurar CORS y Content-Type
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String method = exchange.getRequestMethod();
        String response = "";

        if ("GET".equalsIgnoreCase(method)) {
            // Si la URL tiene un ID (ej: /recording/1), se hace un readById, de lo contrario se retornan todas
            String path = exchange.getRequestURI().getPath();  // Ej: /recording o /recording/1
            String[] segments = path.split("/");
            if (segments.length >= 3) { // Se espera que el tercer segmento sea el ID
                try {
                    int id = Integer.parseInt(segments[2]);
                    Recording rec = recordingRepository.readById(id);
                    if (rec != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", rec.getId());
                        jsonObject.put("audioData", Base64.getEncoder().encodeToString(rec.getAudioData()));
                        jsonObject.put("recordingDate", rec.getRecordingDate());
                        jsonObject.put("duration", rec.getDuration());
                        response = jsonObject.toJSONString();
                        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    } else {
                        response = "{\"error\": \"Recording not found.\"}";
                        exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                    }
                } catch (NumberFormatException e) {
                    response = "{\"error\": \"Invalid ID format.\"}";
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
            } else {
                // Retornar todas las grabaciones
                List<Recording> recordings = recordingRepository.readAll();
                JSONArray jsonArray = new JSONArray();
                for (Recording r : recordings) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", r.getId());
                    jsonObject.put("audioData", Base64.getEncoder().encodeToString(r.getAudioData()));
                    jsonObject.put("recordingDate", r.getRecordingDate());
                    jsonObject.put("duration", r.getDuration());
                    jsonArray.add(jsonObject);
                }
                response = jsonArray.toJSONString();
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(body);
                // Se espera que audioData venga en Base64
                String audioDataStr = json.get("audioData").toString();
                byte[] audioData = Base64.getDecoder().decode(audioDataStr);
                String recordingDate = json.get("recordingDate").toString();
                int duration = Integer.parseInt(json.get("duration").toString());

                Recording recording = new Recording(0, audioData, recordingDate, duration);
                RecordingRepository.save(recording);
                response = "{\"message\": \"Recording saved successfully\"}";
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            } catch (ParseException e) {
                System.err.println("JSON Parse Error: " + e.getMessage());
                response = "{\"error\": \"Invalid JSON format.\"}";
                exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                response = "{\"error\": \"Internal server error.\"}";
                exchange.sendResponseHeaders(500, response.getBytes(StandardCharsets.UTF_8).length);
            }
        } else if ("DELETE".equalsIgnoreCase(method)) {
            // Para DELETE se espera la URL: /recording/{id}
            String path = exchange.getRequestURI().getPath(); // Ejemplo: /recording/1
            String[] segments = path.split("/");
            if (segments.length >= 3) {
                try {
                    int id = Integer.parseInt(segments[2]);
                    recordingRepository.deleteById(id);
                    response = "{\"message\": \"Recording deleted successfully\"}";
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                } catch (NumberFormatException e) {
                    response = "{\"error\": \"Invalid ID format.\"}";
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
            } else {
                response = "{\"error\": \"Missing ID in URL.\"}";
                exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
