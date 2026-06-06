package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import dev.rckft.notificationservice.notification.processing.ChannelAwareNotificationProcessor;

import java.util.Map;
import java.util.Objects;

import static dev.rckft.notificationservice.notification.Channel.*;

class NotificationChannelDeliveryDispatcher implements NotificationDeliveryDispatcher {

    private final Map<Channel, ChannelAwareNotificationProcessor> processors;

    public NotificationChannelDeliveryDispatcher(Map<Channel, ChannelAwareNotificationProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public NotificationProcessingResult dispatch(NotificationDeliveryRequest request) {
        Channel channel = Objects.requireNonNullElse(request.channel(), DEFAULT);
        ChannelAwareNotificationProcessor processor = processors.get(channel);
        return processor.process(request);
    }

}
