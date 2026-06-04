package dev.rckft.notificationservice.notification.dispatcher;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "bean-definitions")
record BeanDefinitionsProperties(
        Set<String> processorsBeans,
        String defaultProcessorBean
) { }
