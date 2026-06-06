package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.Channel;

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
    public NotificationProcessingResult.Builder process(NotificationDeliveryRequest request) {
        NotificationProcessingContext context = new NotificationProcessingContext(request);
        NotificationProcessingResult.Builder processingResultBuilder = NotificationProcessingResult.builder();
        processingResultBuilder.sourceRequestId(request.id());
        try {
            processingSteps.forEach(step -> step.execute(context, processingResultBuilder));
        } catch (NotificationProcessingException exception) {
            processingResultBuilder.error(exception.getMessage());
            return processingResultBuilder;
        }
        return processingResultBuilder;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

}
