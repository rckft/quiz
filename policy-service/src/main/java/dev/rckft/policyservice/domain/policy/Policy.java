package dev.rckft.policyservice.domain.policy;

import java.util.Date;

public class Policy {

    Long id;
    PolicyHolder policyHolder;

    public Policy(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public static Policy create(PolicyHolder policyHolder) {
        if (policyHolder == null) {
            throw new IllegalArgumentException("Cannot create policy without PolicyHolder");
        }
        return new Policy(policyHolder);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }
}
