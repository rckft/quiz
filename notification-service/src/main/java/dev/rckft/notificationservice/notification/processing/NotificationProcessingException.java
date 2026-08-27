package dev.rckft.notificationservice.notification.processing;

public class NotificationProcessingException extends RuntimeException {

    public NotificationProcessingException() {
        super();
    }

    public NotificationProcessingException(String message) {
        super(message);
    }
}
