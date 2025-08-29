package su.opencode.digsigcik.common.exceptions.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private String message;
    private String code;
    private String uri;
    private String traceId;
    private String spanId;

    public ErrorResponse(String message,
                         String uri,
                         String code,
                         String traceId,
                         String spanId) {
        this.timestamp = Instant.now();
        this.message = message;
        this.code = code;
        this.uri = uri.substring(uri.indexOf("/"));
        this.traceId = traceId;
        this.spanId = spanId;
    }
}
