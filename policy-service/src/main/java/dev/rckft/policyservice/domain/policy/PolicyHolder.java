package dev.rckft.policyservice.domain.policy;

public class PolicyHolder {

    String name;
    String lastName;
    Address address;
    String email;

    public PolicyHolder(String name, String lastName, Address address, String email) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
