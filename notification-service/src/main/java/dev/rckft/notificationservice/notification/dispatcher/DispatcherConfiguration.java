package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.log.DeliveryLogRepository;
import dev.rckft.notificationservice.notification.processor.NotificationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


@Configuration
public class DispatcherConfiguration {

    @Bean("notificationStrategyDispatcher")
    public NotificationDispatcher dispatcher(
            Set<NotificationProcessor> notificationProcessors,
            DeliveryLogRepository deliveryLogRepository) {
        return new NotificationChannelDispatcher(notificationProcessors, deliveryLogRepository);
    }

}
