package com.f5.factoria_musical;

import com.f5.factoria_musical.controller.PianoController;
import com.f5.factoria_musical.controller.RecordingController;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;





public  class Server {
       public static void main(String[] args) throws IOException{

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
   
        server.createContext("/piano", new PianoController());
        server.createContext("/recording", new RecordingController());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}