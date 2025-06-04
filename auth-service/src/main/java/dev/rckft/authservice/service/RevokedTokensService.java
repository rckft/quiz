package dev.rckft.authservice.service;

import dev.rckft.authservice.model.user.RevokedToken;
import dev.rckft.authservice.repository.RevokedTokensRepository;
import dev.rckft.authservice.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RevokedTokensService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RevokedTokensService.class);
    private final RevokedTokensRepository revokedTokensRepository;
    private final JwtUtil jwtUtil;

    public RevokedTokensService(RevokedTokensRepository revokedTokensRepository, JwtUtil jwtUtil) {
        this.revokedTokensRepository = revokedTokensRepository;
        this.jwtUtil = jwtUtil;
    }

    public void revokeToken(String refreshToken) {
        String jti = jwtUtil.extractJti(refreshToken);
        Instant expiryDate = jwtUtil.getExpiration(refreshToken).toInstant().plusMillis(JwtUtil.ACCESS_TOKEN_DURATION);
        String username = jwtUtil.extractUsername(refreshToken);
        revokedTokensRepository.save(new RevokedToken(jti, expiryDate));
        LOGGER.info("Revoked refresh token of user {} with jti {} and expiry date of {}", username, jti, expiryDate);
    }

    public boolean isTokenRevoked(String token) {
        String jti = jwtUtil.extractJti(token);
        LOGGER.debug("Searching for revoked token with jti {}", jti);
        boolean isTokenRevoked = revokedTokensRepository.findByJti(jti).isPresent();
        LOGGER.debug("Revoked token with jti {} {}", jti, isTokenRevoked ? "found" : "not found");
        return isTokenRevoked;
    }
}
