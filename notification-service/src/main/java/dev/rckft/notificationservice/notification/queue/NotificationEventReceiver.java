package dev.rckft.notificationservice.notification.queue;

import dev.rckft.notificationservice.notification.delivery.NotificationDeliveryFacade;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventReceiver {

    private final NotificationDeliveryFacade deliveryCoordinator;

    public NotificationEventReceiver(NotificationDeliveryFacade deliveryCoordinator) {
        this.deliveryCoordinator = deliveryCoordinator;
    }

    @RabbitListener(queues = "notificationToSendQueue", concurrency = "1-3")
    public void receive(NotificationToSendEvent event){
        deliveryCoordinator.deliver(event);
    }

}
