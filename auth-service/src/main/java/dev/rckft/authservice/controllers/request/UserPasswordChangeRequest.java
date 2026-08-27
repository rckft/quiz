package dev.rckft.authservice.controllers.request;

public record UserPasswordChangeRequest(String oldPassword, String newPassword) {}