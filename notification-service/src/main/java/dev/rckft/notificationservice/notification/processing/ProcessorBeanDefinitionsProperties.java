package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "bean-definitions.processors")
record ProcessorBeanDefinitionsProperties(
        Map<Channel, String> processorsBeans
) { }
