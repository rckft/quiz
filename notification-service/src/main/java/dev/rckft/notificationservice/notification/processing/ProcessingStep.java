package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;

interface ProcessingStep {

    void execute(NotificationProcessingContext context, NotificationProcessingResult.Builder processingResultBuilder);

}
