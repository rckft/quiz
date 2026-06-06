package dev.rckft.notificationservice.notification.delivery;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultNotificationDeliveryCoordinatorTest {

    private final NotificationDeliveryDispatcher dispatcher = mock(NotificationDeliveryDispatcher.class);
    private final NotificationDeliveryEventPublisher publisher = mock(NotificationDeliveryEventPublisher.class);
    private final NotificationDeliveryFacade coordinator = new DefaultNotificationDeliveryCoordinator(dispatcher, publisher);

    @Test
    void deliver_process_should_call_call_dispatcher_and_publisher() {
        // given
        NotificationProcessingResult result = mock(NotificationProcessingResult.class);
        NotificationDeliveryRequest request = mock(NotificationDeliveryRequest.class);
        when(dispatcher.dispatch(any(NotificationDeliveryRequest.class))).thenReturn(result);

        //when
        coordinator.handle(request);

        // then
        Mockito.verify(dispatcher).dispatch(request);
        Mockito.verify(publisher).publishProcessingFinishedEvent(result);
    }



}
