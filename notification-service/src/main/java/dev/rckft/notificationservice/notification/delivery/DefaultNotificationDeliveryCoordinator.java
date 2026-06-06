package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.NotificationEventPublisher;
import dev.rckft.notificationservice.notification.processing.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;


class DefaultNotificationDeliveryCoordinator implements NotificationDeliveryFacade {

    private final NotificationDeliveryDispatcher dispatcher;
    private final NotificationEventPublisher publisher;

    public DefaultNotificationDeliveryCoordinator(
            NotificationDeliveryDispatcher dispatcher,
            NotificationEventPublisher publisher) {
        this.dispatcher = dispatcher;
        this.publisher = publisher;
    }

    @Override
    public void handle(NotificationDeliveryRequest event) {
        NotificationProcessingResult.Builder result = dispatcher.dispatch(event);
        publisher.publishDeliveryCompletedEvent(result.build());
    }
}
