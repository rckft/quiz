package dev.rckft.policyservice.infrastructure.model;

import jakarta.persistence.*;

@Entity
@Table(name = "policy")
public class PolicyEntity {

    @Id
    @SequenceGenerator(
            name = "policy_seq",
            sequenceName = "policy_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_seq")
    private Long id;
    @OneToOne(cascade = CascadeType.PERSIST)
    private PolicyHolderEntity policyHolderEntity;

    protected PolicyEntity() {
    }

    public PolicyEntity(PolicyHolderEntity policyHolderEntity) {
        this.policyHolderEntity = policyHolderEntity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
