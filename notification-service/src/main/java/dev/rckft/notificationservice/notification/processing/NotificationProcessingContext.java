package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

record NotificationProcessingContext(NotificationDeliveryRequest request) {
    public OutgoingEmail toOutgoingEmail() {
        return OutgoingEmail.builder()
                .from(request.sender())
                .to(request.receiver())
                .subject(request.subject())
                .build();
    }
}
