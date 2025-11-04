package dev.rckft.notificationservice.notification.log;


import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
//TODO - [WIP] - nazwa, czy ten listener będzie słuchał tylko eventów o skończonym cyklu procesowania
//TODO - czy może będzie też słuchał eventów o przerwanym cyklu procesowania np ProcessingInterruptedEvent?
//TODO - czy to będą dwa różne listenery?
//TODO - [NOTE] - parametryzowany listener pozwala na reagowanie na rózne rodzaje eventów dziedziczące po ApplicationEvent
//TODO - i można wtedy zawęźić rodzaj eventów np na extends ProcessingEvent extends ApplicationEvent
//TODO - czy to daje zamknięty na modyfikacje, otwarty na rozszerzenie ? (SOLID)

//TODO [SPOTKANIE]
//TODO - eventy w springu są synchroniczne
//TODO - chce synchronicnie czy asynchronoiczne?
//TODO - jak synchroniczne to kaplica
public class NotificationProcessingFinishedListener implements NotificationProcessingEventListener<ProcessingFinishedEvent> {

    // TODO [ASK] czy między listenerem a repository powinna być jakaś warstwa ?
    private final DeliveryLogRepository repository;

    public NotificationProcessingFinishedListener(DeliveryLogRepository repository) {
        this.repository = repository;
    }

    @EventListener
    @Override
    public void handle(ProcessingFinishedEvent event) {
        // TODO - [WIP] - czy to na pewno handler powinien ustawiać status i błędy ?
        // TODO - status i błędy powinny raczej z eventu przychodzić
        // TODO - ProcessingFinishedEvent i ProcessingInterruptedEvent ?
        NotificationProcessingResult result = event.getResult();

        // TODO - [ERROR] - tutaj leci error po tym, bo result.getErrors zwraca kolejkę obiektów typu
        // TODO - NotificationProcessingError, JDBC albo Spring Data albo coś (dowiedzieć się co)
        // TODO - próbuje to wpakować do relacji/tabeli notification_processing_error
        // TODO - [LEARN, ASK] - dowiedzieć się jak to działa i jak to obejść
        // TODO - [LEARN] - dziwność i niejawowość składni String[]::new
        // TODO - [WIP] na razie errors jako String[] -> psql varchar[], ale docelowo json

        // TODO - [WIP, ASK] jak obsłużyć takiego Exceptiona ?
        // TODO - [THINK] zastanowić się nad całym procesem rzucania błędów w procesowaniu notyfikacji

        // TODO [WIP] może to repozytorium powinno same być na tyle mądre żeby wiedzieć jak to łyknąć?
        // TODO a co jak będę chciał zmienić db? jakoś rozdzielić interfejs od implementacji per baza?
        repository.save(new DeliveryLog(result.getStatus(), result.getErrors()));
    }

    // TODO [SPOTKANIE] co to są te BACZE

}
