package bif3.swe1.API.context;
import lombok.Getter;
import lombok.Setter;

// Store request context
public class ResponseContext {

    @Getter
    private String http_version;
    @Getter
    @Setter
    private String status;
    @Getter
    private String server;
    @Getter
    private String contentType;
    @Getter
    @Setter
    private int contentLength;
    @Getter
    private String payload;

    public ResponseContext(String status){
        http_version = "HTTP/1.1";
        this.status = status;
        server = "mtcg-server";
        contentType = "application/json";
        contentLength = 0;
        payload = "";
    }

    public boolean setPayload(String payload){
        this.payload = payload;
        contentLength = payload.length();
        if (contentLength > 0){
            return true;
        }
        return false;
    }
}
