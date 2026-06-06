package dev.rckft.notificationservice.notification.delivery.event;

import dev.rckft.notificationservice.notification.processing.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.delivery.NotificationDeliveryStatus;
import org.springframework.context.ApplicationEvent;

public class NotificationDeliveryEvent extends ApplicationEvent {
    private final NotificationDeliveryStatus status;
    private final NotificationProcessingResult result;

    public NotificationDeliveryEvent(Object source, NotificationDeliveryStatus status, NotificationProcessingResult result) {
        super(source);
        this.status = status;
        this.result = result;
    }

    public NotificationProcessingResult getResult() {
        return result;
    }

    public NotificationDeliveryStatus getStatus() {
        return status;
    }
}
