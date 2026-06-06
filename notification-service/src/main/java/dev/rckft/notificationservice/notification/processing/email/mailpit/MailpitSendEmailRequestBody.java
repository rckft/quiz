package dev.rckft.notificationservice.notification.processing.email.mailpit;

import java.util.HashSet;
import java.util.Set;

public record MailpitSendEmailRequestBody(MailpitEmailAddress from, Set<MailpitEmailAddress> to) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MailpitEmailAddress from;
        private final Set<MailpitEmailAddress> to = new HashSet<>();

        public Builder from(String email, String name) {
            this.from = new MailpitEmailAddress(email, name);
            return this;
        }

        public Builder to(String email, String name) {
            this.to.add(new MailpitEmailAddress(email, name));
            return this;
        }

        public MailpitSendEmailRequestBody build() {
            return new MailpitSendEmailRequestBody(this.from, this.to);
        }
    }
}

record MailpitEmailAddress(String Email, String Name) { }

