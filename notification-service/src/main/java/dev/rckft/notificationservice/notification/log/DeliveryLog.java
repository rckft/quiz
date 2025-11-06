package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingError;
import dev.rckft.notificationservice.notification.NotificationProcessingStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Set;

// TODO - [DEV] jakie pola jeszcze w tej klasie ?
// TODO - id eventu notyfikacji, coś co pozwoli połączyć delivery log z eventem/notyfikacją

@Entity
public final class DeliveryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationProcessingStatus status;
    @Type(JsonType.class)
    private Set<NotificationProcessingError> errors;

    protected DeliveryLog() {}

    public DeliveryLog(NotificationProcessingStatus status, Set<NotificationProcessingError> errors) {
        this.status = status;
        this.errors = errors;
    }

}