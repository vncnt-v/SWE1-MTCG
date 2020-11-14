package bif3.swe1.API;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

// Store request context
public class RequestContext {
    @Getter
    @Setter
    private String http_verb;
    @Getter
    @Setter
    private String requested;
    @Getter
    @Setter
    private String http_version;
    @Getter
    @Setter
    private Map<String,String> data;
    @Getter
    @Setter
    private String payload;
}
