package dev.rckft.notificationservice.notification.processing;

interface ProcessingStep {

    void execute(NotificationProcessingContext context, NotificationProcessingResult.Builder processingResultBuilder) throws NotificationProcessingException;

}
