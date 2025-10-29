package dev.rckft.notificationservice.notification.steps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepsConfig {

    @Bean
    Step alwaysSuccessStep() {
        return (context, processingResultBuilder) -> processingResultBuilder.success();
    }

}
