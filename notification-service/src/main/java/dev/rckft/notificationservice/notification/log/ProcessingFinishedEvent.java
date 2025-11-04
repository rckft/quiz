package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.ApplicationEvent;

public class ProcessingFinishedEvent extends ApplicationEvent {
    // TODO - [WIP] - Sonar krzyczy o to, że pole result nie jest Serializable
    // TODO - a ApplicationEvent implementuje Serializable
    private final NotificationProcessingResult result;


    // TODO - [NOTE] - jak tworzyć nowy event
    // TODO - (extends ApplicationEvent, dodatkowe pola ustawiane w konstruktorze, po super(source))
    public ProcessingFinishedEvent(Object source, NotificationProcessingResult result) {
        super(source);
        this.result = result;
    }

    public NotificationProcessingResult getResult() {
        return result;
    }
}
