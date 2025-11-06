package dev.rckft.notificationservice;

import dev.rckft.notificationservice.notification.processor.BeanDefinitionsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableConfigurationProperties(BeanDefinitionsProperties.class)
public class AppConfig { }
