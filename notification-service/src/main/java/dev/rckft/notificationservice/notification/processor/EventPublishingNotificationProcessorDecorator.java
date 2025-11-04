package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.log.NotificationProcessingEventPublisher;

//TODO [SPOTKANIE]
// zrezygnowac z dekoratora,
public class EventPublishingNotificationProcessorDecorator implements NotificationProcessor {

    private final NotificationProcessor processor;
    private final NotificationProcessingEventPublisher publisher;

    public EventPublishingNotificationProcessorDecorator(NotificationProcessor processor,
                                                         NotificationProcessingEventPublisher publisher) {
        this.processor = processor;
        this.publisher = publisher;
    }


    @Override
    public NotificationProcessingResult process(NotificationToSendEvent event) {
        NotificationProcessingResult result = this.processor.process(event);
        publisher.publishProcessingFinishedEvent(result);
        return result;
    }

    // TODO - [ASK] podpytać Michała o ten pomysł, żeby owijać notification processor w dekorator
    // TODO - który pozwala na logowanie do bazy delivery log
    // TODO - problem jest taki, że implementując interfejs NotificationProcessor
    // TODO - musi być zaimplementowana metoda getChannel, delegacja jest
    @Override
    public Channel getChannel() {
        return this.processor.getChannel();
    }


}
