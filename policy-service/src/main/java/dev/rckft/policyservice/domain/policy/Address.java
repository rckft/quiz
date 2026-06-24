package dev.rckft.policyservice.domain.policy;

import java.util.Objects;
import java.util.stream.Stream;

public class Address {

    String street;
    String buildingNo;
    String postCode;
    String city;

    public Address(String street, String buildingNo, String postCode, String city) {
        if (Stream.of(street, buildingNo, postCode, city).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("required address fields are not set");
        }
        this.street = street;
        this.buildingNo = buildingNo;
        this.postCode = postCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

}
