package dev.rckft.notificationservice.notification;

import dev.rckft.notificationservice.notification.delivery.event.NotificationDeliveryEvent;
import dev.rckft.notificationservice.notification.processing.NotificationProcessingResult;
import org.springframework.context.ApplicationEventPublisher;

import static dev.rckft.notificationservice.notification.delivery.NotificationDeliveryStatus.*;

public class NotificationEventPublisher {

    private final ApplicationEventPublisher delegateEventPublisher;

    public NotificationEventPublisher(ApplicationEventPublisher delegateEventPublisher) {
        this.delegateEventPublisher = delegateEventPublisher;
    }

    public void publishDeliveryCompletedEvent(NotificationProcessingResult result) {
        delegateEventPublisher.publishEvent(new NotificationDeliveryEvent(this, COMPLETED, result));
    }

    public void publishDeliveryFailedEvent(NotificationProcessingResult result) {
        delegateEventPublisher.publishEvent(new NotificationDeliveryEvent(this, FAILED, result));
    }
}
