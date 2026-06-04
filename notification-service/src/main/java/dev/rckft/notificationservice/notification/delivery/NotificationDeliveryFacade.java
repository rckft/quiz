package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.queue.event.NotificationToDeliverEvent;

public interface NotificationDeliveryFacade {

    void deliver(NotificationToDeliverEvent event);

}
