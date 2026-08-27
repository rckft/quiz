package dev.rckft.notificationservice.notification.processing;

import dev.rckft.notificationservice.notification.NotificationProcessingError;

import java.util.HashSet;
import java.util.Set;

public class NotificationProcessingResult {

    private final Long sourceRequestId;
    private final Set<NotificationProcessingError> errors;

    NotificationProcessingResult (Builder builder) {
        this.sourceRequestId = builder.sourceRequestId;
        this.errors = builder.errors;
    }

    public Long getSourceRequestId() {
        return sourceRequestId;
    }

    public Set<NotificationProcessingError> getErrors() {
        return errors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long sourceRequestId;
        private final Set<NotificationProcessingError> errors = new HashSet<>();

        public void sourceRequestId(Long id) {
            sourceRequestId = id;
        }

        public Builder error(String errorMsg) {
            this.errors.add(new NotificationProcessingError(errorMsg));
            return this;
        }

        public NotificationProcessingResult build() {
            return new NotificationProcessingResult(this);
        }
    }



}
