package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.processing.email.EmailBeanDefinitionsProperties;
import dev.rckft.notificationservice.notification.processing.email.EmailSender;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
class StepsConfig {

    @Bean
    ProcessingStep alwaysSuccessStep() {
        return (context, processingResultBuilder) -> {
        };
    }

    @Bean
    ProcessingStep sendEmailStep(EmailSender emailSender) {
        return new EmailSendingStep(emailSender);
    }

}
