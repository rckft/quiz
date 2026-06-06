package dev.rckft.notificationservice.notification.delivery.event;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.ApplicationEvent;

public class NotificationDeliveryCompletedEvent extends ApplicationEvent {
    private final NotificationProcessingResult result;

    public NotificationDeliveryCompletedEvent(Object source, NotificationProcessingResult result) {
        super(source);
        this.result = result;
    }

    public NotificationProcessingResult getResult() {
        return result;
    }
}
