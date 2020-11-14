package bif3.swe1.API;

import bif3.swe1.mtcg.GameManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("start server...");
        try {
            ServerSocket listener = new ServerSocket(8000, 5);
            System.out.println("Waiting for clients...");
            GameManager manager = GameManager.getInstance();
            while (true) {
                Socket socket = listener.accept();
                System.out.println("New client arrived");
                Thread thread = new Thread(() -> {
                    // Try reader and writer
                    try ( BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                        // Request data
                        RequestContext request;
                        // Unwrap
                        Unwrapper wrapper = new Unwrapper(reader);
                        request = wrapper.unwarp();
                        // Handle response
                        ResponseHandler responder = new ResponseHandler(writer);
                        responder.response(request,manager);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Client gone.");
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}