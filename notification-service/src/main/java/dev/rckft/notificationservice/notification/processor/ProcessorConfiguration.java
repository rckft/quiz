package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.steps.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProcessorConfiguration {

    @Bean
    NotificationProcessor emailNotificationProcessor(@Qualifier("emailNotificationProcessorSteps") List<Step> emailNotificationProcessorSteps) {
        return new StepProcessingNotificationProcessor(emailNotificationProcessorSteps);
    }

    @Bean
    List<Step> emailNotificationProcessorSteps(@Qualifier("alwaysSuccessStep") Step alwaysSuccessStep) {
        return List.of(alwaysSuccessStep);
    }




}
