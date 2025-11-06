package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public interface NotificationDeliveryDispatcher {

    NotificationProcessingResult dispatch(NotificationToSendEvent event);

}
