package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

public interface NotificationDeliveryFacade {

    void handle(NotificationDeliveryRequest request);

}
