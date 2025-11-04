package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.NotificationProcessingStatus;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
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

        NotificationToSendEvent testEmailSubject = new NotificationToSendEvent(
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
