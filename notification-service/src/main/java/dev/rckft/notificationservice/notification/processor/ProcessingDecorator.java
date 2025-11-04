package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public interface ProcessingDecorator {

    public void process(NotificationToSendEvent event);

}
