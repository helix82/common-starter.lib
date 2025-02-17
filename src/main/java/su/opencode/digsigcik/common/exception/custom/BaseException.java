package su.opencode.digsigcik.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class BaseException extends ResponseStatusException {
    private final String code;

    public BaseException(HttpStatus status, String reason, String code) {
        super(status, reason);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getReason();
    }
}
