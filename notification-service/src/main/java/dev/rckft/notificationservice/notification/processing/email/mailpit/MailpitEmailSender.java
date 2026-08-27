package dev.rckft.notificationservice.notification.processing.email.mailpit;

import dev.rckft.notificationservice.notification.EmailAddress;
import dev.rckft.notificationservice.notification.processing.NotificationSendingException;
import dev.rckft.notificationservice.notification.processing.OutgoingEmail;
import dev.rckft.notificationservice.notification.processing.email.EmailSender;
import dev.rckft.notificationservice.notification.processing.email.EmailSendingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

class MailpitEmailSender implements EmailSender {

    @Override
    public EmailSendingResponse sendEmail(OutgoingEmail outgoingEmail) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MailpitResponse> response;
        try {
            response = restTemplate.postForEntity(
                    "http://localhost:8025/api/v1/send",
                    getMailpitSendEmailRequestBody(outgoingEmail),
                    MailpitResponse.class
            );
        } catch (RestClientException exception) {
            throw new NotificationSendingException();
        }

        return new EmailSendingResponse(
                response.getStatusCode().toString(),
                response.getBody().ID(),
                response.getBody().Error()
        );
    }

    private MailpitSendEmailRequestBody getMailpitSendEmailRequestBody(OutgoingEmail outgoingEmail) {
        EmailAddress sender = outgoingEmail.from();
        Set<EmailAddress> receivers = outgoingEmail.to();

        MailpitSendEmailRequestBody.Builder requestBodyBuilder = MailpitSendEmailRequestBody.builder()
                .from(sender.email(), sender.name());

        receivers.forEach(receiver -> requestBodyBuilder.to(receiver.email(), receiver.name()));

        return requestBodyBuilder.build();
    }

}
