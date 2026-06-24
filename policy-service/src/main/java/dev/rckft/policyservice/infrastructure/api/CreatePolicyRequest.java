package dev.rckft.policyservice.infrastructure.api;

public record CreatePolicyRequest(
        String policyHolderName,
        String policyHolderLastName,
        String policyHolderStreet,
        String policyHolderBuildingNo,
        String policyHolderPostCode,
        String policyHolderCity,
        String policyHolderEmail) { }
