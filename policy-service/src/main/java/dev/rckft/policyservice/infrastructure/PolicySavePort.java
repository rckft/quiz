package dev.rckft.policyservice.infrastructure;

import dev.rckft.policyservice.domain.policy.Policy;

public interface PolicySavePort {

    Long savePolicy(Policy policy);

}
