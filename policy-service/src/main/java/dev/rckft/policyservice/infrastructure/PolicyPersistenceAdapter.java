package dev.rckft.policyservice.infrastructure;

import dev.rckft.policyservice.domain.policy.Address;
import dev.rckft.policyservice.domain.policy.Policy;
import dev.rckft.policyservice.domain.policy.PolicyHolder;
import dev.rckft.policyservice.infrastructure.model.PolicyEntity;
import dev.rckft.policyservice.infrastructure.model.PolicyHolderEntity;
import org.springframework.stereotype.Component;

@Component
public class PolicyPersistenceAdapter implements PolicySavePort {

    private final PolicyRepository policyRepository;

    public PolicyPersistenceAdapter(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Long savePolicy(Policy policy) {
        PolicyEntity saved = policyRepository.save(toEntity(policy));
        return saved.getId();
    }

    private PolicyEntity toEntity(Policy policy) {
        PolicyHolder policyHolder = policy.getPolicyHolder();
        Address address = policyHolder.getAddress();
        PolicyHolderEntity policyHolderEntity = new PolicyHolderEntity(
                policyHolder.getName(),
                policyHolder.getLastName(),
                address.getStreet(),
                address.getBuildingNo(),
                address.getPostCode(),
                address.getCity(),
                policyHolder.getEmail()
        );
        return new PolicyEntity(policyHolderEntity);
    }

}
