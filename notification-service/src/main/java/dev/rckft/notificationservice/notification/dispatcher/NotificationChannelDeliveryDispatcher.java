package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.processor.ChannelAwareNotificationProcessor;

import java.util.Set;

class NotificationChannelDeliveryDispatcher implements NotificationDeliveryDispatcher {

    private final Set<ChannelAwareNotificationProcessor> processors;
    private final ChannelAwareNotificationProcessor defaultProcessor;

    public NotificationChannelDeliveryDispatcher(Set<ChannelAwareNotificationProcessor> processors, ChannelAwareNotificationProcessor defaultProcessor) {
        this.processors = processors;
        this.defaultProcessor = defaultProcessor;
    }

    @Override
    public NotificationProcessingResult dispatch(NotificationToSendEvent event) {
        // TODO [DEV] to można dać do oddzielnego selectora, który będzie beanem, przez co można go pewnie skonfigurować,
        // TODO i wtedy dispatcher nie ma pojęcia o kanałach
        // TODO [DEV] mam w notatkach z 08.11.2025 gotową implementacje
        ChannelAwareNotificationProcessor processor = getProcessor(event.channel());
        return processor.process(event);
    }

    private ChannelAwareNotificationProcessor getProcessor(Channel channel) {
        // TODO [DEV] rozważyć ustawienie kanału na DEFAULT i wynieść to wyżej w logice, wtedy tu znika if
        // TODO i będzie można łatwo zmienić logikę wybierana z wysukiwania po kanale
        // TODO na konfiguiracyjne Map<CHANNEL, processor> i wyciągać jednym get
        // TODO wtedy też znika tu pole defaultProcessor
        if (channel == null) {
            return defaultProcessor;
        }

        return this.processors.stream()
                .filter(processor -> processor.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No processor found for channel:" + channel.name()));
    }


}
