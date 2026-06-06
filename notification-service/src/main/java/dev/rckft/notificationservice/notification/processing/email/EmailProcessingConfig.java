package dev.rckft.notificationservice.notification.processing.email;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties(EmailBeanDefinitionsProperties.class)
public class EmailProcessingConfig {

    public static final String FALLBACK_EMAIL_SENDER = "mailpitEmailSender";
    private final ApplicationContext applicationContext;
    private final EmailBeanDefinitionsProperties beanDefinitionsProperties;

    EmailProcessingConfig(
            ApplicationContext applicationContext,
            EmailBeanDefinitionsProperties beanDefinitionsProperties
    ) {
        this.applicationContext = applicationContext;
        this.beanDefinitionsProperties = beanDefinitionsProperties;
    }

    @Bean
    public EmailSender emailSender() {
        String emailSenderBeanName = Objects.requireNonNullElse(
                beanDefinitionsProperties.emailSenderBean(),
                FALLBACK_EMAIL_SENDER
        );

        return applicationContext.getBean(emailSenderBeanName, EmailSender.class);
    }


}
