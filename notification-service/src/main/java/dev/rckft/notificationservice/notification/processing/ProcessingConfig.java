package dev.rckft.notificationservice.notification.processing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.rckft.notificationservice.notification.Channel.EMAIL;

@Configuration
class ProcessingConfig {

    @Bean
    ChannelAwareNotificationProcessor emailNotificationProcessor(
            List<ProcessingStep> emailNotificationProcessorSteps) {
        return new StepProcessingNotificationProcessor(EMAIL, emailNotificationProcessorSteps);
    }

    @Bean
    List<ProcessingStep> emailNotificationProcessorSteps(@Qualifier("alwaysSuccessStep") ProcessingStep alwaysSuccessStep) {
        return List.of(alwaysSuccessStep);
    }




}
