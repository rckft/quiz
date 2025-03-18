package dev.rckft.authservice.scheduler;


import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

public class RevokedTokensScheduler {

    private final RevokedTokensRepository revokedTokensRepository;
    private final Clock clock;

    public RevokedTokensScheduler(RevokedTokensRepository revokedTokensRepository, Clock clock) {
        this.revokedTokensRepository = revokedTokensRepository;
        this.clock = clock;
    }

    @Scheduled(cron = "${scheduler.revoked-tokens.cron}")
    @Transactional
    public void deleteExpiredTokens() {
        revokedTokensRepository.deleteAllByExpiryDateBefore(clock.instant());
    }

}