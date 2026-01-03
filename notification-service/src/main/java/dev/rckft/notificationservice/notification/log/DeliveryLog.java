package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingError;
import dev.rckft.notificationservice.notification.NotificationProcessingStatus;
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
    private NotificationProcessingStatus status;
    @Type(JsonType.class)
    private Set<NotificationProcessingError> errors;

    protected DeliveryLog() {}

    public DeliveryLog(Long sourceRequestId,
                       NotificationProcessingStatus status,
                       Set<NotificationProcessingError> errors) {
        this.sourceRequestId = sourceRequestId;
        this.status = status;
        this.errors = errors;
    }

}