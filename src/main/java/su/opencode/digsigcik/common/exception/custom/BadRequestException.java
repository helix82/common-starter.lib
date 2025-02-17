package su.opencode.digsigcik.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(String reason, String code) {
        super(HttpStatus.BAD_REQUEST, reason, code);
    }
}
