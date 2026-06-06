package dev.rckft.notificationservice.notification.processing.email.mailpit;

import dev.rckft.notificationservice.notification.processing.email.EmailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailpitConfiguration {

    @Bean
    public EmailSender mailpitEmailSender() {
        return new MailpitEmailSender();
    }

}
