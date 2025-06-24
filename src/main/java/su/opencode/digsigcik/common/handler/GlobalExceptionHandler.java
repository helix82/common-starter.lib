package su.opencode.digsigcik.common.handler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import su.opencode.digsigcik.common.exception.ErrorResponse;
import su.opencode.digsigcik.common.exception.custom.BadRequestException;
import su.opencode.digsigcik.common.exception.custom.BaseException;
import su.opencode.digsigcik.common.exception.custom.NotFoundException;

import java.util.NoSuchElementException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Operation(summary = "Обработка неизвестных ошибок", description = "Перехватывает любые неожиданные ошибки и возвращает код ошибки ERROR-005.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                "Внутренняя ошибка сервера.",
                request,
                "ERROR-005"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                ex.getMessage(),
                request,
                ex.getCode()
        );
        return ResponseEntity.status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                ex.getMessage(),
                request,
                ex.getCode()
        );
        return ResponseEntity.status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                ex.getMessage(),
                request,
                "CODE_400"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                ex.getMessage(),
                request,
                "CODE_400"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Некорректный запрос",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse errorResponse = buildErrorResponse(
                ex.getMessage(),
                request,
                "CODE_400"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, WebRequest request) {
        logError(ex, request);
        ErrorResponse response = buildErrorResponse(
                ex.getMessage(),
                request,
                ex.getCode()
        );

        return ResponseEntity.status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    private void logError(Exception ex, WebRequest request) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StackTraceElement element = (stackTrace.length > 0) ? stackTrace[0] : new StackTraceElement("N/A", "N/A", "N/A", -1);

        log.error("[{}] Ошибка в {}.{}(), строка: {}, запрос: {}, сообщение: {}",
                ex.getClass().getSimpleName(),
                element.getClassName(),
                element.getMethodName(),
                element.getLineNumber(),
                request.getDescription(false),
                ex.getMessage(), ex);
    }

    private ErrorResponse buildErrorResponse(String message, WebRequest request, String code) {
        String uri = request.getDescription(false);
        return new ErrorResponse(message, uri, code);
    }
}
