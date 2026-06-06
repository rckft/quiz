package dev.rckft.notificationservice.notification;

import dev.rckft.notificationservice.notification.log.DeliveryLog;
import dev.rckft.notificationservice.notification.log.DeliveryLogRepository;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static dev.rckft.notificationservice.notification.MessagingBrokerTestConfiguration.EXCHANGE_NAME;
import static dev.rckft.notificationservice.notification.MessagingBrokerTestConfiguration.ROUTING_KEY;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Import(MessagingBrokerTestConfiguration.class)
class NotificationDeliveryIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.13-alpine");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DeliveryLogRepository deliveryLogRepository;

    @AfterEach
    void tearDown() {
        deliveryLogRepository.deleteAll();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
        registry.add("spring.rabbitmq.host", RABBIT_MQ_CONTAINER::getHost);
        registry.add("spring.rabbitmq.port", RABBIT_MQ_CONTAINER::getAmqpPort);
    }

    @Test
    void should_produce_delivery_log() {
        //given
        Long requestId = 2137L;
        NotificationDeliveryRequest request = new NotificationDeliveryRequest(
                requestId,
                Channel.EMAIL,
                "test@email.com",
                "test subject",
                "test template id",
                Map.of()
        );

        //when
        brokerSendsMessage(request);

        //then
        assertEquals(1L, deliveryLogRepository.count());
        DeliveryLog log = deliveryLogRepository.findBySourceRequestId(requestId);
        assertNotNull(log);
        assertEquals(requestId, log.getSourceRequestId());
    }

    private void brokerSendsMessage(NotificationDeliveryRequest deliveryEvent) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, deliveryEvent);
    }
}