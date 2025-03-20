package dev.rckft.authservice.scheduler;

import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "scheduler.enabled", havingValue = "true")
public class SchedulerConfig {

    @Bean
    public RevokedTokensScheduler revokedTokensScheduler(RevokedTokensRepository revokedTokensRepository, Clock clock) {
        return new RevokedTokensScheduler(revokedTokensRepository, clock);
    }

}