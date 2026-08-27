package dev.rckft.notificationservice.notification.queue.event;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.EmailAddress;

import java.util.Map;

public record NotificationDeliveryRequest(
        Long id,
        Channel channel,
        EmailAddress sender,
        EmailAddress receiver,
        String subject,
        String templateId,
        Map<String, String> payload
) {}
