package dev.rckft.notificationservice.notification.processor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "bean-definitions")
public record BeanDefinitionsProperties(
        Set<String> processorsBeans,
        String defaultProcessorBean
) { }
