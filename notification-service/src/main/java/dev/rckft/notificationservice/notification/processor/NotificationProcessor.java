package dev.rckft.notificationservice.notification.processor;

import dev.rckft.notificationservice.notification.Channel;
import dev.rckft.notificationservice.notification.NotificationProcessingResult;
import dev.rckft.notificationservice.notification.event.NotificationToSendEvent;

//TODO [SPOTKANIE] dzielić interfejsy,
public interface NotificationProcessor {

    NotificationProcessingResult process(NotificationToSendEvent event);

    Channel getChannel();

}

//public interface WithChannel {
//    // TODO [SPOTKANIE]
//    // defaoultowy channel jako fallback
//    // jak propertisy - to defaultowe wartośći ZAWSZE
//    // dodać logowanie
//    Channel getChannel();
//
//}
