package su.opencode.digsigcik.common;

import su.opencode.digsigcik.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GlobalExceptionHandler.class)
public class ErrorHandlerAutoConfiguration {
}
