package dev.rckft.notificationservice.notification.dispatcher;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.log.DeliveryLogRepository;
import dev.rckft.notificationservice.notification.processor.NotificationProcessor;

import java.util.Set;

// skoro to jest ChannelDispatcher, to znaczy że nie każdy event będzie miał channel ?
class NotificationChannelDispatcher implements NotificationDispatcher {

    private final Set<NotificationProcessor> processors;
    private final DeliveryLogRepository deliveryLogRepository;

    public NotificationChannelDispatcher(Set<NotificationProcessor> processors, DeliveryLogRepository deliveryLogRepository) {
        this.processors = processors;
        this.deliveryLogRepository = deliveryLogRepository;
    }

    // czy dispatch ma zawsze wywoływać processor.process(event) ? jeśli tak, to może to powinna być metoda abstrakcyjna z nadpisaniem ?
    // pozwolić na to żeby inne dispatchery mogły nie wywołać tej metody processor process ?
    // być może inne dispatchery nie będą działać na podstawie processorów, i processory są częścią tej dokładnie implementacji dispatchera ?
    // jak to wpłynie na testy ??
    @Override
    public NotificationProcessingResult dispatch(NotificationToSendEvent event) {
        // to można dać do oddzielnego selectora, który będzie beanem, przez co można go pewnie skonfigurować, i wtedy dispatcher nie ma pojęcia o kanałach
        NotificationProcessor processor = getProcessor(event.channel());
        return processor.process(event);
    }

    private NotificationProcessor getProcessor(Channel channel) {
        NotificationProcessor notificationProcessor = this.processors.stream()
                .filter(processor -> processor.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No processor found for channel:" + channel.name()));
        return notificationProcessor;
    }


}
