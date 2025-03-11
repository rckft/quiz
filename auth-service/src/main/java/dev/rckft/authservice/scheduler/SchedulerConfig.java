package dev.rckft.authservice.scheduler;

import dev.rckft.authservice.repository.RevokedTokensRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public RevokedTokensScheduler revokedTokensScheduler(RevokedTokensRepository revokedTokensRepository) {
        return new RevokedTokensScheduler(revokedTokensRepository);
    }

}