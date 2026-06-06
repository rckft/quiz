package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.processing.email.EmailSender;

class EmailSendingStep implements ProcessingStep {

    EmailSender sender;

    public EmailSendingStep(EmailSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(
            NotificationProcessingContext context,
            NotificationProcessingResult.Builder processingResultBuilder
    ) throws NotificationProcessingException {
        //czy ten błąd notification sending error poleci mi do góry ?
        try {
            sender.sendEmail(context.toOutgoingEmail());
        } catch (NotificationSendingException e) {
            throw new NotificationProcessingException();
        }

    }

}
