package dev.rckft.policyservice.application;

import dev.rckft.policyservice.domain.policy.Address;
import dev.rckft.policyservice.domain.policy.Policy;
import dev.rckft.policyservice.domain.policy.PolicyHolder;
import dev.rckft.policyservice.infrastructure.PolicySavePort;
import dev.rckft.policyservice.infrastructure.api.CreatePolicyRequest;

class CreatePolicyService implements CreatePolicyUseCase  {

    private final PolicySavePort policySavePort;

    public CreatePolicyService(PolicySavePort policySavePort) {
        this.policySavePort = policySavePort;
    }

    @Override
    public Policy createPolicy(CreatePolicyRequest createPolicyRequest) {
        PolicyHolder policyHolder = new PolicyHolder(
                createPolicyRequest.policyHolderName(),
                createPolicyRequest.policyHolderLastName(),
                new Address(
                        createPolicyRequest.policyHolderStreet(),
                        createPolicyRequest.policyHolderBuildingNo(),
                        createPolicyRequest.policyHolderPostCode(),
                        createPolicyRequest.policyHolderCity()),
                createPolicyRequest.policyHolderEmail()
        );
        Policy policy = Policy.create(policyHolder);
        Long id = policySavePort.savePolicy(policy);
        policy.setId(id);
        return policy;
    }

}
