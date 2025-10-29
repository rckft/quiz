package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.NotificationContext;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.steps.Step;

import java.util.List;

class StepProcessingNotificationProcessor implements NotificationProcessor {

    private final List<Step> steps;

    public StepProcessingNotificationProcessor(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public NotificationProcessingResult process(NotificationToSendEvent event) {
        NotificationContext context = new NotificationContext(event);
        NotificationProcessingResult.Builder processingResultBuilder = new NotificationProcessingResult.Builder();
        steps.forEach(step -> step.execute(context, processingResultBuilder));
        return processingResultBuilder.build();
    }

}
