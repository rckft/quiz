package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.ApplicationEvent;

// TODO [DEV] od wersji spring boota 4.2 nie trzeba używać ApplicationEvent
public class ProcessingFinishedEvent extends ApplicationEvent {
    // TODO - [DEV] - Sonar krzyczy o to, że pole result nie jest Serializable
    // TODO - a ApplicationEvent implementuje Serializable
    private final NotificationProcessingResult result;

    public ProcessingFinishedEvent(Object source, NotificationProcessingResult result) {
        super(source);
        this.result = result;
    }

    public NotificationProcessingResult getResult() {
        return result;
    }
}
