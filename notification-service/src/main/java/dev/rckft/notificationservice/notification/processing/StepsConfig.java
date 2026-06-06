package dev.rckft.notificationservice.notification.processing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StepsConfig {

    @Bean
    ProcessingStep alwaysSuccessStep() {
        return (context, processingResultBuilder) -> {
            processingResultBuilder.success();
        };
    }

}
