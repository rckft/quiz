package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

record NotificationProcessingContext(NotificationDeliveryRequest request) {}
