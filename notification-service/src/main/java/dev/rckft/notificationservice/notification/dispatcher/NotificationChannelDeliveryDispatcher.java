package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import dev.rckft.notificationservice.notification.processing.ChannelAwareNotificationProcessor;

import java.util.Set;

class NotificationChannelDeliveryDispatcher implements NotificationDeliveryDispatcher {

    private final Set<ChannelAwareNotificationProcessor> processors;
    private final ChannelAwareNotificationProcessor defaultProcessor;

    public NotificationChannelDeliveryDispatcher(Set<ChannelAwareNotificationProcessor> processors, ChannelAwareNotificationProcessor defaultProcessor) {
        this.processors = processors;
        this.defaultProcessor = defaultProcessor;
    }

    @Override
    public NotificationProcessingResult dispatch(NotificationDeliveryRequest request) {
        ChannelAwareNotificationProcessor processor = getProcessor(request.channel());
        return processor.process(request);
    }

    private ChannelAwareNotificationProcessor getProcessor(Channel channel) {
        if (channel == null) {
            return defaultProcessor;
        }

        return this.processors.stream()
                .filter(processor -> processor.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No processor found for channel:" + channel.name()));
    }


}
