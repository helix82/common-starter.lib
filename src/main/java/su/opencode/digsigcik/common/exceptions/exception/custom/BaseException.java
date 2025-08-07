package su.opencode.digsigcik.common.exceptions.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public abstract class BaseException extends ResponseStatusException {
    private final String code;

    public BaseException(HttpStatus status, String reason) {
        super(status, reason);
        this.code = getDefaultCode();
    }

    public BaseException(HttpStatus status, String reason, String code) {
        super(status, reason);
        this.code = code;
    }

    protected abstract String getDefaultCode();

    @Override
    public String getMessage() {
        return super.getReason();
    }
}
