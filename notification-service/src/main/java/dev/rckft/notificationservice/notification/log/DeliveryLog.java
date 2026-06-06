package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingError;
import dev.rckft.notificationservice.notification.delivery.NotificationDeliveryStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Set;

@Entity
public final class DeliveryLog {

    @Id
    @SequenceGenerator(
            name = "delivery_log_seq",
            sequenceName = "delivery_log_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_log_seq")
    private Long id;
    private Long sourceRequestId;
    @Enumerated(EnumType.STRING)
    private NotificationDeliveryStatus status;
    @Type(JsonType.class)
    private Set<NotificationProcessingError> errors;

    protected DeliveryLog() {}

    public DeliveryLog(Long sourceRequestId,
                       NotificationDeliveryStatus status,
                       Set<NotificationProcessingError> errors) {
        this.sourceRequestId = sourceRequestId;
        this.status = status;
        this.errors = errors;
    }

    public Long getSourceRequestId() {
        return sourceRequestId;
    }

    public NotificationDeliveryStatus getStatus() {
        return status;
    }

    public Set<NotificationProcessingError> getErrors() {
        return errors;
    }
}