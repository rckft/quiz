package dev.rckft.policyservice.application;

import dev.rckft.policyservice.infrastructure.PolicySavePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    CreatePolicyUseCase createPolicyUseCase(PolicySavePort policySavePort) {
        return new CreatePolicyService(policySavePort);
    }

}
