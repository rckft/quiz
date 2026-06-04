package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.queue.event.NotificationToDeliverEvent;

record NotificationProcessingContext(NotificationToDeliverEvent event) {}
