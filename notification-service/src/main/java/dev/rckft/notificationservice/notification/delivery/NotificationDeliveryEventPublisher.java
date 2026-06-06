package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.delivery.event.NotificationDeliveryCompletedEvent;
import org.springframework.context.ApplicationEventPublisher;

class NotificationDeliveryEventPublisher {

    private final ApplicationEventPublisher delegateEventPublisher;

    public NotificationDeliveryEventPublisher(ApplicationEventPublisher delegateEventPublisher) {
        this.delegateEventPublisher = delegateEventPublisher;
    }

    public void publishProcessingFinishedEvent(NotificationProcessingResult result) {
        delegateEventPublisher.publishEvent(new NotificationDeliveryCompletedEvent(this, result));
    }
}
