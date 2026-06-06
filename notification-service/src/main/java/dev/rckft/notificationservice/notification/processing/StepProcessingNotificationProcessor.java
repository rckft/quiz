package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.queue.event.NotificationDeliveryRequest;

import java.util.List;

class StepProcessingNotificationProcessor implements ChannelAwareNotificationProcessor {

    private final List<ProcessingStep> processingSteps;
    private final Channel channel;

    public StepProcessingNotificationProcessor(Channel channel, List<ProcessingStep> steps) {
        this.channel = channel;
        this.processingSteps = steps;
    }

    @Override
    public NotificationProcessingResult process(NotificationDeliveryRequest request) {
        NotificationProcessingContext context = new NotificationProcessingContext(request);
        NotificationProcessingResult.Builder processingResultBuilder = new NotificationProcessingResult.Builder();
        processingResultBuilder.sourceRequestId(request.id());
        processingSteps.forEach(step -> step.execute(context, processingResultBuilder));
        return processingResultBuilder.build();
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
