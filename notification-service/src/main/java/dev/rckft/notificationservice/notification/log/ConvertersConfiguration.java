package dev.rckft.notificationservice.notification.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.sql.SQLException;
import java.util.List;

@Configuration
// TODO [NOTE] potrzebny jest customowy konwerter, bo spring data jdbc nie potrafi ładować json stringów do psql jsonb
// TODO [THINK] jako, że tu są szczegóły że baza jest psql, to można to wpakować do profilu postgres czy coś

// TODO [SPOTKANIE]
// JDBC to może lepiej tam, gdzie potrzebne szybkie wyszukiwanie
// hibernate tu pasuje

public class ConvertersConfiguration {

    @Bean
    JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(
                List.of(new DeliveryLogProcessingErrorsToJsonbConverter())
        );
    }

    // TODO [NOTE] ta adnotacja absolutnie nie jest tu potrzebna, jest tylko po to żebym nie zwariował i wiedział po co to
    @WritingConverter
    static class DeliveryLogProcessingErrorsToJsonbConverter implements Converter<DeliveryLog.ProcessingErrors, PGobject> {

        //TODO [WIP] ten mapper to nie tu, pewnie najlepiej go wynieśc do beana i współdzielić
        ObjectMapper mapper = new ObjectMapper();

        @Override
        // TODO [WIP, THINK] ten rekord z klasy DeliveryLog... trzeba pogłówkować
        // TODO a dlaczego tu jest rekord ProcessingErrors który tylko owija Set<NotificationProcessingError> ?
        // TODO ano dlatego, że dla Spring Data JDBC każda kolekcja to jest relacja child table dlatego próbuje
        // TODO zrobić inserta do tabeli notification_processing_error, nawet jak mam konwerter który przerabia to
        // TODO na json stringa, ale jak już to jest record to nie ma problemu...

        // TODO [NOTE] MOŻE JEDNAK WARTO TEN KOMBAJN HIBERNATE, TAM ROBI SIĘ TO 1(!) ADNOTACJĄ

        public PGobject convert(DeliveryLog.ProcessingErrors source) {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            try {
                pgObject.setValue(mapper.writeValueAsString(source.errors()));
            } catch (SQLException e) {
                // TODO [WIP, THINK] stworzyć system rzucania i łapania wyjątków w processingu notyfikacji
                // TODO chociaż czy to na pewno jest processing notyfikacji ? delivery log jest zapisywany
                // TODO w wyniku przechwycenia eventu, a nie jest częścią processingu
                throw new IllegalStateException("value is invalid for this type");
            } catch (JsonProcessingException e) {
                // TODO [WIP] nie rzucać runtimeException, to jest za szerokie pojęcie
                throw new RuntimeException("json conversion failed");
            }
            return pgObject;
        }
    }


}
