package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationToDeliverEvent;

import java.util.List;

class StepProcessingNotificationProcessor implements ChannelAwareNotificationProcessor {

    private final List<ProcessingStep> processingSteps;
    private final Channel channel;

    public StepProcessingNotificationProcessor(Channel channel, List<ProcessingStep> steps) {
        this.channel = channel;
        this.processingSteps = steps;
    }

    @Override
    public NotificationProcessingResult process(NotificationToDeliverEvent event) {
        NotificationProcessingContext context = new NotificationProcessingContext(event);
        NotificationProcessingResult.Builder processingResultBuilder = new NotificationProcessingResult.Builder();
        processingSteps.forEach(step -> step.execute(context, processingResultBuilder));
        return processingResultBuilder.build();
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
