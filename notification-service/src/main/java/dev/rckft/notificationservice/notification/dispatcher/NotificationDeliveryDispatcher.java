package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationToDeliverEvent;

public interface NotificationDeliveryDispatcher {

    NotificationProcessingResult dispatch(NotificationToDeliverEvent event);

}
