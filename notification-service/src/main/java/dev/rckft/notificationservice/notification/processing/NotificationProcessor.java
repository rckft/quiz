package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationToDeliverEvent;

interface NotificationProcessor {

    NotificationProcessingResult process(NotificationToDeliverEvent event);

}

