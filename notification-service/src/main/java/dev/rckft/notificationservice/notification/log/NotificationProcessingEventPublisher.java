package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationProcessingEventPublisher {

    private final ApplicationEventPublisher delegateEventPublisher;

    public NotificationProcessingEventPublisher(ApplicationEventPublisher delegateEventPublisher) {
        this.delegateEventPublisher = delegateEventPublisher;
    }

    public void publishProcessingFinishedEvent(NotificationProcessingResult result) {
        delegateEventPublisher.publishEvent(new ProcessingFinishedEvent(this, result));
    }
}
