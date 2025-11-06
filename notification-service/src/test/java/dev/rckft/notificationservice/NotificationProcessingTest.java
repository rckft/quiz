package dev.rckft.notificationservice;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.queue.NotificationEventReceiver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class NotificationProcessingTest {


    @Autowired
    NotificationEventReceiver receiver;

    @Test
    public void e2e_happy_path_() {
        NotificationToSendEvent event = new NotificationToSendEvent(
                Channel.EMAIL,
                "john.doe@test.com",
                "Test email subject",
                "test-template-id",
                Map.of());

        receiver.receive(event);

    }


}
