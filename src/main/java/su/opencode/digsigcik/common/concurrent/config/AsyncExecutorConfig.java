package su.opencode.digsigcik.common.concurrent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import su.opencode.digsigcik.common.concurrent.factory.ExecutorCreator;

@Configuration
@ConditionalOnProperty(
        prefix = "async",
        name = "enabled-executors",
        havingValue = "true")
@EnableAsync
@Slf4j
public class AsyncExecutorConfig implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private Environment environment;
    private static final String ASYNC_PREFIX = "async";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Binder binder = Binder.get(environment);
        AsyncExecutorProperties asyncExecutorProperties = binder
                .bind(ASYNC_PREFIX, Bindable.of(AsyncExecutorProperties.class))
                .orElseThrow(() ->
                        new IllegalStateException("Failed to bind AsyncExecutorProperties from application.yml or application.properties"));

        ExecutorCreator executorCreator = new ExecutorCreator();
        int availableCpus = Runtime.getRuntime().availableProcessors();

        log.info("Number of CPU: {}", availableCpus);

        asyncExecutorProperties.getExecutors().forEach((name, config) -> {
            RootBeanDefinition executorBeanDefinition = executorCreator.createExecutorBeanDefinition(name, config, availableCpus);
            registry.registerBeanDefinition(name, executorBeanDefinition);

            log.info("Registered ThreadPoolTaskExecutor bean: {}", name);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
