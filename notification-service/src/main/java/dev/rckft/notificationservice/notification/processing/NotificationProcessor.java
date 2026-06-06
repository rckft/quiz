package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

interface NotificationProcessor {

    NotificationProcessingResult.Builder process(NotificationDeliveryRequest request);

}

