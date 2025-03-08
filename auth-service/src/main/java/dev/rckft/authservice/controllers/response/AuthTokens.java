package dev.rckft.authservice.controllers.response;

public record AuthTokens(String accessToken, String refreshToken) {}