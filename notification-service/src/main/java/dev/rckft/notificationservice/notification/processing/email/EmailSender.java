package dev.rckft.notificationservice.notification.processing.email;

import dev.rckft.notificationservice.notification.processing.NotificationSendingException;
import dev.rckft.notificationservice.notification.processing.OutgoingEmail;

public interface EmailSender {

    EmailSendingResponse sendEmail(OutgoingEmail outgoingEmail) throws NotificationSendingException;

}