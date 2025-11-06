package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public interface NotificationDeliveryFacade {

    void deliver(NotificationToSendEvent event);

}
