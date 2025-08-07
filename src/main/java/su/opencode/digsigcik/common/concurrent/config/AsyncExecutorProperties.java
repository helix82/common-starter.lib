package su.opencode.digsigcik.common.concurrent.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@ToString
@Log4j2
@Validated
@ConfigurationProperties(prefix = "async")
public class AsyncExecutorProperties {

    @NotEmpty
    private Map<String, ExecutorConfig> executors;

    @Getter
    @Setter
    @ToString
    public static class ExecutorConfig {
        /**
         * Целевое использование CPU (0.0 – 1.0), например 0.4 означает 40%.
         */
        @NotNull(message = "targetCpuUtilization must be set")
        private double targetCpuUtilization;

        /**
         * Время, затрачиваемое задачей на операции ввода-вывода (в миллисекундах).
         */
        @NotNull(message = "ioTimeMs must be set")
        private int ioTimeMs;

        /**
         * Время, затрачиваемое задачей на выполнение на CPU (в миллисекундах).
         */
        @NotNull(message = "cpuTimeMs must be set")
        private int cpuTimeMs;
        /**
         * Максимальное количество задач, которые могут находиться в очереди ожидания
         * перед выполнением потоком. Используется, если все потоки заняты.
         */
        @NotNull(message = "queueCapacity must be set")
        private int queueCapacity;
    }


    @PostConstruct
    void init() {
        log.info("AsyncExecutorProperties init: {}", this);
    }
}
