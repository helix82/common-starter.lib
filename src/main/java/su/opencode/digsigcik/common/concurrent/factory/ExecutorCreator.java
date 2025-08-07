package su.opencode.digsigcik.common.concurrent.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import su.opencode.digsigcik.common.concurrent.config.AsyncExecutorProperties;
import su.opencode.digsigcik.common.utils.CPUUtils;

import java.util.concurrent.Executor;

@Log4j2
public class ExecutorCreator {

    public Executor createExecutor(String executorName,
                                   AsyncExecutorProperties.ExecutorConfig executorConfig,
                                   int availableCpus) {

        int optimalThreads = CPUUtils.calculateOptimalThreadCount(
                availableCpus,
                executorConfig.getTargetCpuUtilization(),
                executorConfig.getIoTimeMs(),
                executorConfig.getCpuTimeMs());

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(optimalThreads);
        executor.setMaxPoolSize(optimalThreads * 2);
        executor.setQueueCapacity(executorConfig.getQueueCapacity());
        executor.setThreadNamePrefix(String.format("Async-%s-", executorName));
        executor.initialize();
        log.info("Configured thread pool size for {}: {}; maximum: {}", executorName, executor.getCorePoolSize(), executor.getMaxPoolSize());
        return executor;
    }
}
