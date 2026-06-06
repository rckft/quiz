package dev.rckft.notificationservice.notification.queue.event;

import dev.rckft.notificationservice.notification.Channel;

import java.util.Map;

public record NotificationDeliveryRequest(
        Long id,
        Channel channel,
        String email,
        String subject,
        String templateId,
        Map<String, String> payload
) {}
