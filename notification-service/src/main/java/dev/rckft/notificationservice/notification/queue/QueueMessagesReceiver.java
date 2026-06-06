package dev.rckft.notificationservice.notification.queue;

import dev.rckft.notificationservice.notification.delivery.NotificationDeliveryFacade;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

class QueueMessagesReceiver {

    private final NotificationDeliveryFacade deliveryFacade;

    public QueueMessagesReceiver(NotificationDeliveryFacade deliveryFacade) {
        this.deliveryFacade = deliveryFacade;
    }

    @RabbitListener(queues = "${notifications.queue}", concurrency = "1-3")
    public void receive(NotificationDeliveryRequest request){
        deliveryFacade.handle(request);
    }

}
