package dev.rckft.notificationservice.notification;

public class NotificationProcessingResult {

    private final NotificationStatus status;
    private final String error;

    NotificationProcessingResult (Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public static class Builder {
        private static final String STATUS_EXCEPTION_MSG = "Cannot mark notification as SUCCES when current status is ";
        private NotificationStatus status = NotificationStatus.PENDING;
        private String error;

        public Builder success() {
            if (this.status != NotificationStatus.PENDING) {
                throw new IllegalStateException(STATUS_EXCEPTION_MSG + this.status.toString());
            }
            this.status = NotificationStatus.SUCCESS;
            return this;
        }

        public Builder error(String errorMsg) {
            if (this.status != NotificationStatus.PENDING) {
                throw new IllegalStateException(STATUS_EXCEPTION_MSG + this.status.toString());
            }
            this.status = NotificationStatus.ERROR;
            this.error = errorMsg;
            return this;
        }

        public NotificationProcessingResult build() {
            return new NotificationProcessingResult(this);
        }
    }



}
