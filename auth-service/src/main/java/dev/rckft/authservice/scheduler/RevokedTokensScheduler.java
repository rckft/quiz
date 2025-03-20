package dev.rckft.authservice.scheduler;


import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

public class RevokedTokensScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RevokedTokensScheduler.class);

    private final RevokedTokensRepository revokedTokensRepository;
    private final Clock clock;

    public RevokedTokensScheduler(RevokedTokensRepository revokedTokensRepository, Clock clock) {
        this.revokedTokensRepository = revokedTokensRepository;
        this.clock = clock;
    }

    @Scheduled(cron = "${scheduler.revoked-tokens.cron}")
    @Transactional
    public void deleteExpiredTokens() {
        LOGGER.info("Running scheduled job: revoked tokens cleanup job");
        int numberOfTokensDeleted = revokedTokensRepository.deleteAllByExpiryDateBefore(clock.instant());
        LOGGER.info("Job completed, removed {} tokens", numberOfTokensDeleted);
    }

}