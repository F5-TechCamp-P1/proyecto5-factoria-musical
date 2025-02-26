package com.f5.factoria_musical.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.f5.factoria_musical.repository.RecordingRepository;
import com.f5.factoria_musical.model.Recording;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
            sb.append("[Hola Equipo Factoria Musical, estas son las grabaciones: ");
            for (Recording r : recordings) {
                sb.append(r.getDetails()).append(",");
            }
            if (!recordings.isEmpty()) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            response = sb.toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } else if ("POST".equalsIgnoreCase(method)) {

            InputStream is = exchange.getRequestBody();
            byte[] data = is.readAllBytes();

            Recording recording = new Recording(1, data, "2025-02-18", 120);
            RecordingRepository.save(recording);
            response = "{\"message\": \"Recording saved successfully\"}";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } else {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
