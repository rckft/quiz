package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.processor.NotificationProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static dev.rckft.notificationservice.notification.Channel.EMAIL;

@Configuration
public class DispatcherConfiguration {

    @Bean("notificationStrategyDispatcher")
    public NotificationDispatcher dispatcher(
            @Qualifier("emailNotificationProcessor") NotificationProcessor emailNotificationProcessor) {
        return new NotificationChannelDispatcher(Map.of(
                EMAIL, emailNotificationProcessor
        ));
    }

}
