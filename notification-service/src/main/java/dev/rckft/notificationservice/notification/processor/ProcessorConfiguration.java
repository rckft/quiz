package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.log.NotificationProcessingEventPublisher;
import dev.rckft.notificationservice.notification.steps.Step;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.rckft.notificationservice.notification.Channel.EMAIL;

@Configuration
public class ProcessorConfiguration {


    // TODO [ASK] jak wyjąć stąd dekoratora, i bardziej konfiguracyjnie do tego podejść.
    // TODO [SPOTKANIE] WYJAC QUALIFIERY DO PROPERTASÓW
    @Bean
    //            List<Notifcation procxesory> - zdefiniowane w propertasach
//            TODO [SPOTKANIE] na zasadzie instancji systemy (np per klient)
    NotificationProcessor emailNotificationProcessor(
            @Qualifier("emailNotificationProcessorSteps") List<Step> emailNotificationProcessorSteps,
//            List<Step> emailNotificationProcessorSteps,
            @Qualifier("notificationProcessingEventPublisher") NotificationProcessingEventPublisher notificationProcessingEventPublisher
    ) {
        return new EventPublishingNotificationProcessorDecorator(// NotificationProcessor
                new StepProcessingNotificationProcessor(EMAIL, emailNotificationProcessorSteps), // NotificationProcessor, WithChannel
                notificationProcessingEventPublisher
        );
    }

    @Bean
    List<Step> emailNotificationProcessorSteps(@Qualifier("alwaysSuccessStep") Step alwaysSuccessStep) {
        return List.of(alwaysSuccessStep);
    }




}
