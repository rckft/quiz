package dev.rckft.authservice.scheduler;


import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

public class RevokedTokensScheduler {

    private final RevokedTokensRepository revokedTokensRepository;

    public RevokedTokensScheduler(RevokedTokensRepository revokedTokensRepository) {
        this.revokedTokensRepository = revokedTokensRepository;
    }

    @Scheduled(cron = "0 */15 * * * ?")
    public void deleteExpiredTokens() {
        revokedTokensRepository.deleteAllByExpiryDateBefore(Instant.now());
    }

}