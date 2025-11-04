package dev.rckft.notificationservice.notification.log;

import dev.rckft.notificationservice.notification.NotificationProcessingError;
import dev.rckft.notificationservice.notification.NotificationProcessingStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;


// TODO - [NOTE] - autogeneracja id - postgres/liquibase autoIncrement="true"
// TODO - [ASK] setter dla id, więc nie może być final - dopytać Michała
// TODO - [NOTE] dlatego klasa nie rekord
// TODO - [WIP] jakie pola jeszcze w tej klasie ?
// TODO - id eventu notyfikacji, coś co pozwoli połączyć delivery log z eventem/notyfikacją

// TODO - [NOTE] @GeneratedValue jest z JPA (dlatego nie mam jej w podpowiedziach, bo nie mam zależności do JPA)
// TODO - obecnie używam tylko Spring Data z JDBC - spring-boot-starter-data-jdbc

@Table("deliverylog")
public final class DeliveryLog {
    @Id
    // TODO [SPOTKANIE]
//    @GeneratedValue() - tak czy siak lepiej zostawić to bazie danych
    private Long id;
    private final NotificationProcessingStatus status;
//    TODO [NOTE] do tego potrzebny jest hibernate... JEDNA ADNOTACJA
    // TODO [SPOTKANIE] // do hibernate są liby do konwersji do jsonb
//    @ColumnTransformer(write = "?::jsonb")
    @Column("errors") private final ProcessingErrors errors;

    public DeliveryLog(NotificationProcessingStatus status, Set<NotificationProcessingError> errors) {
//        this.id = null;
        this.status = status;
        this.errors = new ProcessingErrors(errors);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long id() {
        return id;
    }

    public NotificationProcessingStatus status() {
        return status;
    }

    public Set<NotificationProcessingError> errors() {
        // TODO [WIP] errors.errors :faceplam: a masło.masło
        return errors.errors;
    }

    //TODO - [WIP] - może jakoś formatować ten errors do logów ?
    @Override
    public String toString() {
        return "DeliveryLog[" +
                "id=" + id + ", " +
                "status=" + status + ", " +
                "errors=" + errors + ']';
    }

    public record ProcessingErrors(Set<NotificationProcessingError> errors) {}
}