package dev.rckft.notificationservice.integration;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDeliveryDispatcher;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmailNotificationProcessorTest {

    @Autowired
    @Qualifier("notificationStrategyDispatcher")
    NotificationDeliveryDispatcher dispatcher;

    @Test
    void testEmailProcessingResult() {
        //given
        NotificationToSendEvent event = new NotificationToSendEvent(
                Channel.EMAIL,
                "john.doe@test.com",
                "Test email subject",
                "test-template-id",
                Map.of());

        //when
        dispatcher.dispatch(event);

        //then
//        assertThat(result.getStatus()).isEqualTo(SUCCESS);
    }

}
