package dev.rckft.notificationservice.notification;

import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public record NotificationContext(NotificationToSendEvent event) {}
