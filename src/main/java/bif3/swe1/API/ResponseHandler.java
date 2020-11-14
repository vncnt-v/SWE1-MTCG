package bif3.swe1.API;

import bif3.swe1.mtcg.GameManager;

import java.io.BufferedWriter;
import java.io.IOException;

// Handle request and send response
public class ResponseHandler {

    BufferedWriter writer;

    public ResponseHandler(BufferedWriter writer){
        this.writer = writer;
    }

    public void response(RequestContext request, GameManager manager) {
        String payload = "";
        // Check resource requested
        String[] parts = request.getRequested().split("/");
        if ((parts.length == 2 || parts.length == 3) && parts[1].equals("messages")  ){
            if (parts.length == 3) {
                int number = Integer.parseInt(parts[2]);
                switch (request.getHttp_verb()){
                    case "GET":
                        // manager
                        //payload = storage.getMsg(number);
                        break;
                    case "PUT":

                        break;
                    case "DELETE":

                        break;
                    default:
                        //ERR
                        break;
                }
            } else {
                switch (request.getHttp_verb()){
                    case "GET":

                        break;
                    case "POST":

                        break;
                    default:
                        //ERR
                        break;
                }
            }
        } else {
            payload = "ERR .../messages";
        }
        // Create response
        String response = "";
        response += request.getHttp_version() + " 200 OK\r\n";
        response += "Server: MailServer\r\n";
        response += "Content-Type: text/html\r\n";
        response += "Content-Length: " + payload.length() + "\r\n\r\n";
        response += payload;
        // Send response
        try {
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
