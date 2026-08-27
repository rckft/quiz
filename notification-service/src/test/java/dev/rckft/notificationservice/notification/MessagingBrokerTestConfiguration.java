package dev.rckft.notificationservice.notification;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.amqp.core.Binding.DestinationType.*;

@TestConfiguration
public class MessagingBrokerTestConfiguration {

    public static final String EXCHANGE_NAME = "notificationToSendExchange";
    public static final String ROUTING_KEY = "notification.to.send.#";

    @Bean
    Queue notificationToSendQueue(@Value("${notifications.queue}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    Exchange notificationToSendExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding notificationToSendBinding(Queue notificationToSendQueue, Exchange notificationToSendExchange) {
        return new Binding(
                notificationToSendQueue.getActualName(),
                QUEUE,
                notificationToSendExchange.getName(),
                ROUTING_KEY,
                null
        );
    }

}
