package dev.rckft.notificationservice.notification.queue;

import dev.rckft.notificationservice.notification.delivery.NotificationDeliveryFacade;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QueueConfig {

    @Bean
    public QueueMessagesReceiver notificationEventReceiver(NotificationDeliveryFacade notificationDeliveryFacade) {
        return new QueueMessagesReceiver(notificationDeliveryFacade);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
