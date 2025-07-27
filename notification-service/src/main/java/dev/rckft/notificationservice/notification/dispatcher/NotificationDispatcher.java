package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

public interface NotificationDispatcher {

    void dispatch(NotificationToSendEvent event);

}
