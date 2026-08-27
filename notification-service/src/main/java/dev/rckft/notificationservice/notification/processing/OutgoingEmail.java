package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.EmailAddress;

import java.util.HashSet;
import java.util.Set;

public record OutgoingEmail(
        EmailAddress from,
        Set<EmailAddress> to,
        Set<EmailAddress> cc,
        Set<EmailAddress> bcc,
        EmailAddress replyTo,
        String subject,
        String textBody
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EmailAddress from;
        private final Set<EmailAddress> to = new HashSet<>();
        private final Set<EmailAddress> cc = new HashSet<>();
        private final Set<EmailAddress> bcc = new HashSet<>();
        private EmailAddress replyTo;
        private String subject;
        private String textBody;

        public Builder from(EmailAddress from) {
            this.from = from;
            return this;
        }

        public Builder to(EmailAddress to) {
            this.to.add(to);
            return this;
        }

        public Builder cc(EmailAddress cc) {
            this.cc.add(cc);
            return this;
        }

        public Builder bcc(EmailAddress bcc) {
            this.bcc.add(bcc);
            return this;
        }

        public Builder replyTo(EmailAddress replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder textBody(String textBody) {
            this.textBody = textBody;
            return this;
        }

        public OutgoingEmail build() {
            return new OutgoingEmail(from, to, cc, bcc, replyTo, subject, textBody);
        }
    }
}

