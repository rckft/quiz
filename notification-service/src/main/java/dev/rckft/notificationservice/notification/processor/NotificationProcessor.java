package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public interface NotificationProcessor {

    NotificationProcessingResult process(NotificationToSendEvent event);

}
