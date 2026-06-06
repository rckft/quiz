package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

public interface NotificationDeliveryDispatcher {

    NotificationProcessingResult dispatch(NotificationDeliveryRequest request);

}
