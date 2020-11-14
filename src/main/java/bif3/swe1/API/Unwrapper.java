package bif3.swe1.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// unwrap request and saves in RequestContext
public class Unwrapper {

    BufferedReader reader;

    public Unwrapper(BufferedReader reader){
        this.reader = reader;
    }

    public RequestContext unwarp() {
        RequestContext request = new RequestContext();
        Map<String, String> buffer = new HashMap<>();
        String message;
        try {
            // HTTP-Verb, resource requested, http-version
            message = reader.readLine();
            System.out.println(message);
            String[] parts = message.split(" ");
            request.setHttp_verb(parts[0]);
            request.setRequested(parts[1]);
            request.setHttp_version(parts[2]);
            // further header values
            do {
                message = reader.readLine();
                System.out.println(message);
                // Key -> Value
                parts = message.split(" ", 2);
                if (parts.length == 2){
                    buffer.put(parts[0],parts[1]);
                }
            } while (!message.equals(""));
            request.setData(buffer);
            // Payload
            String payload;
            StringBuilder sb = new StringBuilder();
            while(reader.ready()){
                sb.append(((char)reader.read()));
            }
            payload = sb.toString();
            request.setPayload(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }
}
