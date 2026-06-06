package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.NotificationProcessingStatus;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EmailProcessingTest {

    @Test
    void resultStatusShouldBeSuccess_whenSetByStepToSuccess() {
        //given
        NotificationProcessor processor = new StepProcessingNotificationProcessor(
                Channel.EMAIL,
                List.of(
                        (context, resultBuilder) -> resultBuilder.success()
                )
        );

        NotificationDeliveryRequest testEmailSubject = new NotificationDeliveryRequest(
                1L,
                Channel.EMAIL,
                "john.doe@test.com",
                "Test email subject",
                "test-template-id",
                Map.of());

        //when
        NotificationProcessingResult result = processor.process(testEmailSubject);

        //then
        assertThat(result.getStatus()).isEqualTo(NotificationProcessingStatus.SUCCESS);
    }
}
