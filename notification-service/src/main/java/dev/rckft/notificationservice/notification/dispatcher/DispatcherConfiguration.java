package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.processor.ChannelAwareNotificationProcessor;
import dev.rckft.notificationservice.notification.processor.BeanDefinitionsProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;


@Configuration
public class DispatcherConfiguration {

    private final ApplicationContext context;
    private final BeanDefinitionsProperties beanDefinitionsProperties;

    public DispatcherConfiguration(ApplicationContext context, BeanDefinitionsProperties beanDefinitionsProperties) {
        this.context = context;
        this.beanDefinitionsProperties = beanDefinitionsProperties;
    }

    @Bean("notificationStrategyDispatcher")
    public NotificationDeliveryDispatcher dispatcher() {
        return new NotificationChannelDeliveryDispatcher(processors(), defaultProcessor());
    }

    private Set<ChannelAwareNotificationProcessor> processors() {
        return beanDefinitionsProperties.processorsBeans().stream()
                .map(name -> context.getBean(name, ChannelAwareNotificationProcessor.class))
                .collect(Collectors.toSet());
    }

    private ChannelAwareNotificationProcessor defaultProcessor() {
        return context.getBean(beanDefinitionsProperties.defaultProcessorBean(), ChannelAwareNotificationProcessor.class);
    }

}
