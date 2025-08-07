package su.opencode.digsigcik.common.exceptions.exception.custom;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public NotFoundException(String reason, String code) {
        super(HttpStatus.BAD_REQUEST, reason, code);
    }

    @Override
    protected String getDefaultCode() {
        return "CODE_400";
    }
}
