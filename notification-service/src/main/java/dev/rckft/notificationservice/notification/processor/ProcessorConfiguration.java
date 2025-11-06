package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.steps.ProcessingStep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.rckft.notificationservice.notification.Channel.EMAIL;

@Configuration
public class ProcessorConfiguration {

    @Bean
    ChannelAwareNotificationProcessor emailNotificationProcessor(
            List<ProcessingStep> emailNotificationProcessorSteps) {
        return new StepProcessingNotificationProcessor(EMAIL, emailNotificationProcessorSteps);
    }

    @Bean
    // TODO [THINK, ASK] czy config stepów również wynieść do propertasów
    List<ProcessingStep> emailNotificationProcessorSteps(@Qualifier("alwaysSuccessStep") ProcessingStep alwaysSuccessStep) {
        return List.of(alwaysSuccessStep);
    }




}
