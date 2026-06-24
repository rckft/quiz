package dev.rckft.policyservice.infrastructure.model;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_holder")
public class PolicyHolderEntity {

    @Id
    @SequenceGenerator(
            name = "policy_holder_seq",
            sequenceName = "policy_holder_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_holder_seq")
    private Long id;

    private String name;
    private String lastName;
    private String street;
    private String buildingNo;
    private String postCode;
    private String city;
    private String email;

    protected PolicyHolderEntity() {
    }

    public PolicyHolderEntity(String name, String lastName, String street, String buildingNo,
                              String postCode, String city, String email) {
        this.name = name;
        this.lastName = lastName;
        this.street = street;
        this.buildingNo = buildingNo;
        this.postCode = postCode;
        this.city = city;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

}
