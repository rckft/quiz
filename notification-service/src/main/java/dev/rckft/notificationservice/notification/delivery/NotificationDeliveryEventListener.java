package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.processing.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.delivery.event.NotificationDeliveryEvent;
import dev.rckft.notificationservice.notification.log.DeliveryLog;
import dev.rckft.notificationservice.notification.log.DeliveryLogRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

class NotificationDeliveryEventListener {

    private final DeliveryLogRepository repository;

    public NotificationDeliveryEventListener(DeliveryLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    @Async
    public void handle(NotificationDeliveryEvent event) {
        NotificationProcessingResult result = event.getResult();
        repository.save(new DeliveryLog(
                result.getSourceRequestId(),
                event.getStatus(),
                result.getErrors())
        );
    }


}
