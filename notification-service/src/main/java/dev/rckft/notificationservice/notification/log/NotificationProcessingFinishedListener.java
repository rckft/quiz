package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessingFinishedListener {

    private final DeliveryLogRepository repository;

    public NotificationProcessingFinishedListener(DeliveryLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    @Async
    public void handle(ProcessingFinishedEvent event) {
        NotificationProcessingResult result = event.getResult();
        repository.save(new DeliveryLog(result.getStatus(), result.getErrors()));
    }


}
