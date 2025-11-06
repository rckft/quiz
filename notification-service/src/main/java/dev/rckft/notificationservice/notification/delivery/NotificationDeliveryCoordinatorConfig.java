package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.log.NotificationProcessingEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NotificationDeliveryCoordinatorConfig {

    @Bean("defaultNotificationDeliveryCoordinator")
    NotificationDeliveryFacade defaultNotificationDeliveryCoordinator(
            NotificationDeliveryDispatcher dispatcher,
            NotificationProcessingEventPublisher publisher) {
        return new DefaultNotificationDeliveryCoordinator(dispatcher, publisher);
    }


}
