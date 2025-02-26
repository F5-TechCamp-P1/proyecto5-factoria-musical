package com.f5.factoria_musical.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.f5.factoria_musical.repository.RecordingRepository;
import com.f5.factoria_musical.model.Recording;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import java.util.List;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RecordingController implements HttpHandler {
    private RecordingRepository recordingRepository;

    public RecordingController() {

        this.recordingRepository = new RecordingRepository();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");

        String method = exchange.getRequestMethod();
        String response = "";

        if ("GET".equalsIgnoreCase(method)) {

            List<Recording> recordings = recordingRepository.readAll();
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            for (Recording r : recordings) {
                sb.append(r.getDetails()).append(",");
            }
            if (!recordings.isEmpty()) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            response = sb.toString();
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        } else if ("POST".equalsIgnoreCase(method)) {

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(body);

                String audioDataStr = json.get("audioData").toString();
                byte[] audioData = audioDataStr.getBytes(StandardCharsets.UTF_8);
                String recordingDate = json.get("recordingDate").toString();
                int duration = Integer.parseInt(json.get("duration").toString());

                Recording recording = new Recording(0, audioData, recordingDate, duration);
                RecordingRepository.save(recording);
                response = "{\"message\": \"Recording saved successfully\"}";
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            } catch (ParseException e) {
                response = "{\"error\": \"Invalid JSON format.\"}";
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