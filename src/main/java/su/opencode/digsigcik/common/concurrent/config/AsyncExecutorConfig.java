package su.opencode.digsigcik.common.concurrent.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import su.opencode.digsigcik.common.concurrent.factory.ExecutorCreator;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Configuration
@ConditionalOnProperty(
        prefix = "async",
        name = "enabled-executors",
        havingValue = "true")
@EnableConfigurationProperties(AsyncExecutorProperties.class)
@EnableAsync
@Log4j2
@RequiredArgsConstructor
public class AsyncExecutorConfig {
    private final AsyncExecutorProperties asyncExecutorProperties;
    private final ConfigurableListableBeanFactory beanFactory;


    @PostConstruct
    public void postProcessBeanDefinitionRegistry() throws BeansException {
        int availableCpus = Runtime.getRuntime().availableProcessors();
        log.info("Number of CPU: {}", availableCpus);

        ExecutorCreator executorCreator = new ExecutorCreator();

        asyncExecutorProperties.getExecutors().forEach((executorName, executorConfig) -> {
            Executor executor = executorCreator.createExecutor(executorName, executorConfig, availableCpus);
            beanFactory.registerSingleton(executorName, executor);
        });
    }
}
