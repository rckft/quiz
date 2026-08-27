package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.EmailAddress;
import dev.rckft.notificationservice.notification.NotificationProcessingError;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StepProcessingNotificationProcessorTest {

    @Test
    void resultShouldHaveNoErrors_whenAllStepsSucceed() {
        //given
        NotificationProcessor processor = new StepProcessingNotificationProcessor(
                Channel.DEFAULT,
                List.of(
                        (context, resultBuilder) -> {}
                )
        );

        //when
        NotificationProcessingResult result = processor.process(deliveryRequest()).build();

        //then
        assertThat(result.getErrors()).isEmpty();
    }

    @Test
    void resultShouldHaveError_whenProcessingStepThrows_NotificationProcessingException(){
        //given
        NotificationProcessor processor = new StepProcessingNotificationProcessor(
                Channel.DEFAULT,
                List.of(
                        (context, resultBuilder) -> {
                            throw new NotificationProcessingException("Some nasty exception");
                        }
                )
        );

        //when
        NotificationProcessingResult process = processor.process(deliveryRequest()).build();

        //then
        Set<NotificationProcessingError> errors = process.getErrors();
        assertThat(errors)
                .isNotEmpty()
                .size().isEqualTo(1).returnToIterable()
                .allMatch(it -> it.msg().equals("Some nasty exception"));
    }

    private NotificationDeliveryRequest deliveryRequest() {
        EmailAddress address = new EmailAddress("john.doe@test.com", "John Doe");

        return new NotificationDeliveryRequest(
                1L,
                Channel.DEFAULT,
                address,
                address,
                "Test Subject",
                "test-template-id",
                Map.of()
        );
    }

}
