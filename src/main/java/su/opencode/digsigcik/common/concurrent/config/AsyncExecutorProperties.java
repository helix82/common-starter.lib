package su.opencode.digsigcik.common.concurrent.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class AsyncExecutorProperties {

    private Map<String, ExecutorConfig> executors;

    @Getter
    @Setter
    @ToString
    public static class ExecutorConfig {
        /**
         * Целевое использование CPU (0.0 – 1.0), например 0.4 означает 40%.
         */
        private double targetCpuUtilization;

        /**
         * Время, затрачиваемое задачей на операции ввода-вывода (в миллисекундах).
         */
        private int ioTimeMs;

        /**
         * Время, затрачиваемое задачей на выполнение на CPU (в миллисекундах).
         */
        private int cpuTimeMs;
        /**
         * Максимальное количество задач, которые могут находиться в очереди ожидания
         * перед выполнением потоком. Используется, если все потоки заняты.
         */
        private int queueCapacity;
    }
}
