package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.log.NotificationProcessingEventPublisher;


class DefaultNotificationDeliveryCoordinator implements NotificationDeliveryFacade {

    private final NotificationDeliveryDispatcher dispatcher;
    private final NotificationProcessingEventPublisher publisher;

    public DefaultNotificationDeliveryCoordinator(
            NotificationDeliveryDispatcher dispatcher,
            NotificationProcessingEventPublisher publisher) {
        this.dispatcher = dispatcher;
        this.publisher = publisher;
    }

    @Override
    public void deliver(NotificationToSendEvent event) {
        NotificationProcessingResult result = dispatcher.dispatch(event);
        publisher.publishProcessingFinishedEvent(result);
    }
}
