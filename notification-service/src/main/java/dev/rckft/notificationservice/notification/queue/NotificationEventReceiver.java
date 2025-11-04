package dev.rckft.notificationservice.notification.queue;

import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.dispatcher.NotificationDispatcher;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;
import dev.rckft.notificationservice.notification.processor.NotificationProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventReceiver {

    private final NotificationDispatcher dispatcher;

    NotificationEventReceiver(@Qualifier("notificationStrategyDispatcher") NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    // TODO [SPOTKANIE] jednowątkowy czy nie ? sprawdzić
    // jmeter/postman - jakieś testy wydajnościowe
    // endpoint do kolejki x1000 requestów
    @RabbitListener(queues = "notificationToSendQueue")
    public void receive(NotificationToSendEvent event){
        // TODO
        // troche niejasna nazwa - dispatcher robi dispatch i zwraca result ?
        // nazwa dispatcher jest ok, ale troche to mylące, result to wynik końcowy procesowania,
        // nie dispatcha
        NotificationProcessingResult result = dispatcher.dispatch(event);

//        result.process(event)
//        tu wyjście na świat, skoro jest tu kolejka, to tu również będzie odpowiedź na kolejkę
//        a może wyjście na kolejkę będzie gdzieś dalej.
    }

}
