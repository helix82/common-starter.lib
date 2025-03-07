package su.opencode.digsigcik.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public BadRequestException(String reason, String code) {
        super(HttpStatus.BAD_REQUEST, reason, code);
    }

    @Override
    protected String getDefaultCode() {
        return "CODE_400";
    }
}
