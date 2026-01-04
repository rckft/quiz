package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.rckft.notificationservice.notification.Channel.*;

@Configuration
@EnableConfigurationProperties(ProcessorBeanDefinitionsProperties.class)
class ProcessingConfig {


    private final ApplicationContext context;
    private final ProcessorBeanDefinitionsProperties processorBeanDefinitionsProperties;

    ProcessingConfig(ApplicationContext context, ProcessorBeanDefinitionsProperties processorBeanDefinitionsProperties) {
        this.context = context;
        this.processorBeanDefinitionsProperties = processorBeanDefinitionsProperties;
    }
    @Bean
    Map<Channel, ChannelAwareNotificationProcessor> processors() {
        return processorBeanDefinitionsProperties.processorsBeans().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> context.getBean(entry.getValue(), ChannelAwareNotificationProcessor.class)
                ));
    }

    @Bean
    ChannelAwareNotificationProcessor emailNotificationProcessor(List<ProcessingStep> emailNotificationProcessorSteps) {
        return new StepProcessingNotificationProcessor(EMAIL, emailNotificationProcessorSteps);
    }

    @Bean
    ChannelAwareNotificationProcessor smsNotificationProcessor() {
        return new StepProcessingNotificationProcessor(SMS, List.of());
    }




}
