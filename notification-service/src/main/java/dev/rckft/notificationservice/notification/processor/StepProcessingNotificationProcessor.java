package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationContext;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.steps.ProcessingStep;

import java.util.List;

class StepProcessingNotificationProcessor implements ChannelAwareNotificationProcessor {

    private final List<ProcessingStep> processingSteps;
    private final Channel channel;

    public StepProcessingNotificationProcessor(Channel channel, List<ProcessingStep> steps) {
        this.channel = channel;
        this.processingSteps = steps;
    }

    @Override
    public NotificationProcessingResult process(NotificationToSendEvent event) {
        NotificationContext context = new NotificationContext(event);
        NotificationProcessingResult.Builder processingResultBuilder = new NotificationProcessingResult.Builder();
        processingSteps.forEach(step -> step.execute(context, processingResultBuilder));
        return processingResultBuilder.build();
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
