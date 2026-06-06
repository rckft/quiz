package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;


class DefaultNotificationDeliveryCoordinator implements NotificationDeliveryFacade {

    private final NotificationDeliveryDispatcher dispatcher;
    private final NotificationDeliveryEventPublisher publisher;

    public DefaultNotificationDeliveryCoordinator(
            NotificationDeliveryDispatcher dispatcher,
            NotificationDeliveryEventPublisher publisher) {
        this.dispatcher = dispatcher;
        this.publisher = publisher;
    }

    @Override
    public void handle(NotificationDeliveryRequest event) {
        NotificationProcessingResult result = dispatcher.dispatch(event);
        publisher.publishProcessingFinishedEvent(result);
    }
}
