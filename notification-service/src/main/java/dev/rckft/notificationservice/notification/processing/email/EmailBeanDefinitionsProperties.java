package dev.rckft.notificationservice.notification.processing.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bean-definitions.email")
public record EmailBeanDefinitionsProperties(String emailSenderBean) { }
