package dev.rckft.notificationservice.notification.event;

import dev.rckft.notificationservice.notification.Channel;

import java.util.Map;

public record NotificationToSendEvent(
        Channel channel,
        String email,
        String subject,
        String templateId,
        Map<String, String> payload
) {}
