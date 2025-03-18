package dev.rckft.authservice.scheduler;

import dev.rckft.authservice.model.user.RevokedToken;
import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = {
        """
        scheduler.enabled = true
        scheduler.revoked-tokens.cron = */5 * * * * ?
        """
})
class RevokedTokensSchedulerTest {

    @Autowired
    private RevokedTokensRepository revokedTokensRepository;

    @Test
    void shouldDeleteExpiredTokens() {
        //given
        Instant now = Clock.systemDefaultZone().instant();
        revokedTokensRepository.saveAll(List.of(
                new RevokedToken("EXPIRED_TOKEN_1", now.minus(1, ChronoUnit.MINUTES)),
                new RevokedToken("EXPIRED_TOKEN_2", now.minus(1, ChronoUnit.DAYS)),
                new RevokedToken("VALID_TOKEN_1", now.plus(1, ChronoUnit.MINUTES)),
                new RevokedToken("VALID_TOKEN_2", now.plus(1, ChronoUnit.DAYS))
        ));

        //when
        await().until(() -> revokedTokensRepository.findAll().size() == 2);
    }
}