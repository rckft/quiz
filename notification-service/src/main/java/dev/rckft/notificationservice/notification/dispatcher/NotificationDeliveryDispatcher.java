package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.processing.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

public interface NotificationDeliveryDispatcher {

    NotificationProcessingResult.Builder dispatch(NotificationDeliveryRequest request);

}
