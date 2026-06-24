package dev.rckft.policyservice.application;

import dev.rckft.policyservice.domain.policy.Policy;
import dev.rckft.policyservice.infrastructure.api.CreatePolicyRequest;

public interface CreatePolicyUseCase {

    Policy createPolicy(CreatePolicyRequest createPolicyRequest);

}
