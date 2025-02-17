package su.opencode.digsigcik.common.handler;

import lombok.extern.log4j.Log4j2;
import su.opencode.digsigcik.common.exception.ErrorResponse;
import su.opencode.digsigcik.common.exception.custom.BadRequestException;
import su.opencode.digsigcik.common.exception.custom.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex, WebRequest request) {
        log.error("Internal error: " + ex);
        return buildErrorResponse(
                "Внутренняя ошибка сервера.",
                request,
                "ERROR-005"
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("BadRequestException error: " + ex.getMessage());
        return buildErrorResponse(
                ex.getMessage(),
                request,
                ex.getCode()
        );
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, WebRequest request) {
        log.error("BaseException error: " + ex.getMessage());
        ErrorResponse response = buildErrorResponse(
                ex.getMessage(),
                request,
                ex.getCode()
        );

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    private ErrorResponse buildErrorResponse(String message, WebRequest request, String code) {
        String uri = request.getDescription(false);
        return new ErrorResponse(message, uri, code);
    }
}
