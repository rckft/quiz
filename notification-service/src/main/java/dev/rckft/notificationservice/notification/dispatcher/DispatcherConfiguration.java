package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.processing.ChannelAwareNotificationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
class DispatcherConfiguration {

    @Bean("notificationStrategyDispatcher")
    public NotificationDeliveryDispatcher dispatcher(Map<Channel, ChannelAwareNotificationProcessor> processors) {
        return new NotificationChannelDeliveryDispatcher(processors);
    }

}
