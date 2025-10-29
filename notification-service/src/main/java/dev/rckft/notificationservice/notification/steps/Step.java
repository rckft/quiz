package dev.rckft.notificationservice.notification.steps;

import dev.rckft.notificationservice.notification.NotificationContext;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;

public interface Step {

    void execute(NotificationContext context, NotificationProcessingResult.Builder processingResultBuilder);

}
