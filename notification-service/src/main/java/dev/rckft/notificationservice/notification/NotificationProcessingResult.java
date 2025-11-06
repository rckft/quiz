package dev.rckft.notificationservice.notification;

import java.util.HashSet;
import java.util.Set;

public class NotificationProcessingResult {

    private final NotificationProcessingStatus status;
    private final Set<NotificationProcessingError> errors;

    NotificationProcessingResult (Builder builder) {
        this.status = builder.status;
        this.errors = builder.errors;
    }

    public NotificationProcessingStatus getStatus() {
        return status;
    }

    public Set<NotificationProcessingError> getErrors() {
        return errors;
    }

    public static class Builder {
        private static final String STATUS_EXCEPTION_MSG = "Cannot mark notification as SUCCESS when current status is";
        private NotificationProcessingStatus status = NotificationProcessingStatus.PENDING;
        private final Set<NotificationProcessingError> errors = new HashSet<>();

        public Builder success() {
            if (this.status != NotificationProcessingStatus.PENDING) {
                throw new IllegalStateException(STATUS_EXCEPTION_MSG + this.status.toString());
            }
            this.status = NotificationProcessingStatus.SUCCESS;
            return this;
        }

        public Builder error(String errorMsg) {
            //TODO - [DEV] walidacja, czy errorMsg jest null
            if (this.status != NotificationProcessingStatus.PENDING) {
                throw new IllegalStateException(STATUS_EXCEPTION_MSG + this.status.toString());
            }
            this.status = NotificationProcessingStatus.ERROR;
            this.errors.add(new NotificationProcessingError(errorMsg));
            return this;
        }

        public NotificationProcessingResult build() {
            return new NotificationProcessingResult(this);
        }
    }



}
