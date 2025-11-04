package dev.rckft.notificationservice.notification.log;

import org.springframework.context.ApplicationEvent;

// TODO - [LEARN] - zgłebić wiedzę o typach parametryzowanych
// TODO - przy implementacji interfejsu (tak żeby metoda override mogła użyć parametru A extends B)
public interface NotificationProcessingEventListener<E extends ApplicationEvent> {

    void handle(E event);
}
