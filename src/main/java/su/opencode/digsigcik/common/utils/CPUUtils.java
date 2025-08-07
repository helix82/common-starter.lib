package su.opencode.digsigcik.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CPUUtils {
    /**
     * Рассчитывает рекомендованное количество потоков для Executor с учётом CPU и IO времени.
     *
     * @param availableCores      Количество доступных CPU ядер
     * @param targetCpuUtilization Целевая загрузка CPU (от 0 до 1)
     * @param ioWaitTimeMs         Время ожидания IO (миллисекунды)
     * @param cpuProcessingTimeMs  Время вычислительной работы CPU (миллисекунды)
     * @return Рекомендуемое количество потоков
     */
    public static int calculateOptimalThreadCount(int availableCores, double targetCpuUtilization, int ioWaitTimeMs, int cpuProcessingTimeMs) {
        if (cpuProcessingTimeMs == 0) {
            throw new IllegalArgumentException("cpuProcessingTimeMs не может быть 0");
        }
        double ioToCpuRatio = (double) ioWaitTimeMs / cpuProcessingTimeMs;
        double threads = availableCores * targetCpuUtilization * (1 + ioToCpuRatio);
        return (int) Math.ceil(threads);
    }
}
