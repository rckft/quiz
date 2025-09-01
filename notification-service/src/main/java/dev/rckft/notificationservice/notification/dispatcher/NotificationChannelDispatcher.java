package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.processor.NotificationProcessor;

import java.util.Set;

class NotificationChannelDispatcher implements NotificationDispatcher {

    private final Set<NotificationProcessor> processors;

    public NotificationChannelDispatcher(Set<NotificationProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void dispatch(NotificationToSendEvent event) {
        Channel channel = event.channel();
        NotificationProcessor notificationProcessor = this.processors.stream()
                .filter(processor -> processor.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No processor found for channel:" + channel.name()));
        notificationProcessor.process(event);
    }


}
