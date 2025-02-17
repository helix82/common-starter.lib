package su.opencode.digsigcik.common.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String code;
    private String uri;

    public ErrorResponse(String message, String uri, String code) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.code = code;
        this.uri = uri.substring(uri.indexOf("/"));
    }
}
