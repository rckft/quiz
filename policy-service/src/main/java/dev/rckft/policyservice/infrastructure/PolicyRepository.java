package dev.rckft.policyservice.infrastructure;

import dev.rckft.policyservice.infrastructure.model.PolicyEntity;
import org.springframework.data.repository.CrudRepository;

public interface PolicyRepository extends CrudRepository<PolicyEntity, Long> {

}
