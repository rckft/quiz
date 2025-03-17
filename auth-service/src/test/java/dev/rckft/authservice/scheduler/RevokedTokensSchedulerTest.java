package dev.rckft.authservice.scheduler;

import dev.rckft.authservice.model.user.RevokedToken;
import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RevokedTokensSchedulerTest {

    @Autowired
    private RevokedTokensRepository revokedTokensRepository;

    @Autowired
    private RevokedTokensScheduler scheduler;

    @Test
    @Transactional
    void shouldDeleteExpiredTokens() {
        //given
        Instant now = Instant.now();
        revokedTokensRepository.saveAll(List.of(
                new RevokedToken("EXPIRED_TOKEN_1", now.minus(1, ChronoUnit.MINUTES)),
                new RevokedToken("EXPIRED_TOKEN_2", now.minus(1, ChronoUnit.DAYS)),
                new RevokedToken("VALID_TOKEN_1", now.plus(1, ChronoUnit.MINUTES)),
                new RevokedToken("VALID_TOKEN_2", now.plus(1, ChronoUnit.DAYS))
        ));

        //when
        scheduler.deleteExpiredTokens();

        //then
        List<RevokedToken> revokedTokens = revokedTokensRepository.findAll();

        assertEquals(2, revokedTokens.size());
        List<String> jtis = revokedTokens.stream().map(RevokedToken::getJti).toList();
        assertTrue(jtis.stream().noneMatch(jti -> jti.contains("EXPIRED_TOKEN")));
        assertTrue(jtis.stream().allMatch(jti -> jti.contains("VALID_TOKEN")));
    }
}