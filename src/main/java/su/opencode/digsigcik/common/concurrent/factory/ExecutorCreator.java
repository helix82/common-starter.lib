package su.opencode.digsigcik.common.concurrent.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import su.opencode.digsigcik.common.concurrent.config.AsyncExecutorProperties;
import su.opencode.digsigcik.common.utils.CPUUtils;

@Slf4j
public class ExecutorCreator {
    private static final int MAX_POOL_SIZE_MULTIPLIER = 2;
    private static final String THREAD_NAME_PREFIX_FORMAT = "Async-%s-";

    public RootBeanDefinition createExecutorBeanDefinition(String executorName,
                                                           AsyncExecutorProperties.ExecutorConfig executorConfig,
                                                           int availableCpus) {
        int optimalThreads = CPUUtils.calculateOptimalThreadCount(
                availableCpus,
                executorConfig.getTargetCpuUtilization(),
                executorConfig.getIoTimeMs(),
                executorConfig.getCpuTimeMs());

        RootBeanDefinition beanDefinition = new RootBeanDefinition(ThreadPoolTaskExecutor.class);
        beanDefinition.setInitMethodName("initialize");

        beanDefinition.getPropertyValues().add("corePoolSize", optimalThreads);
        beanDefinition.getPropertyValues().add("maxPoolSize", optimalThreads * 2);
        beanDefinition.getPropertyValues().add("queueCapacity", executorConfig.getQueueCapacity());
        beanDefinition.getPropertyValues().add("threadNamePrefix", String.format(THREAD_NAME_PREFIX_FORMAT, executorName));

        log.info("Configured thread pool definition for {}: core={}, max={}, queueCapacity={},",
                executorName,
                optimalThreads,
                optimalThreads * MAX_POOL_SIZE_MULTIPLIER,
                executorConfig.getQueueCapacity());

        return beanDefinition;
    }
}
