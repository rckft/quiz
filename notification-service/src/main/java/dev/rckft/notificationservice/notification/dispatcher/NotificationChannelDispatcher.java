package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.processor.NotificationProcessor;

import java.util.Map;

class NotificationChannelDispatcher implements NotificationDispatcher {

    private final Map<Channel, NotificationProcessor> channels;

    public NotificationChannelDispatcher(Map<Channel, NotificationProcessor> channels) {
        this.channels = channels;
    }

    @Override
    public void dispatch(NotificationToSendEvent event) {
        NotificationProcessor notificationProcessor = channels.get(event.channel());
        notificationProcessor.process(event);
    }


}
