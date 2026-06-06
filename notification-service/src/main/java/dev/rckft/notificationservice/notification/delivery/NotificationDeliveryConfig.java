package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.log.DeliveryLogRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class NotificationDeliveryConfig {

    @Bean
    NotificationDeliveryFacade defaultNotificationDeliveryCoordinator(
            NotificationDeliveryDispatcher dispatcher,
            NotificationDeliveryEventPublisher publisher) {
        return new DefaultNotificationDeliveryCoordinator(dispatcher, publisher);
    }

    @Bean
    NotificationDeliveryEventListener notificationDeliveryEventListener(DeliveryLogRepository repository) {
        return new NotificationDeliveryEventListener(repository);
    }

    @Bean
    NotificationDeliveryEventPublisher notificationDeliveryEventPublisher(ApplicationEventPublisher delegateEventPublisher) {
        return new NotificationDeliveryEventPublisher(delegateEventPublisher);
    }


}
