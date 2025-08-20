package dev.rckft.notificationservice.notification.queue;

import dev.rckft.notificationservice.notification.dispatcher.NotificationDispatcher;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventReceiver {

    private final NotificationDispatcher dispatcher;

    NotificationEventReceiver(@Qualifier("notificationStrategyDispatcher") NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @RabbitListener(queues = "notificationToSendQueue")
    public void receive(NotificationToSendEvent event){
        dispatcher.dispatch(event);
    }

}
