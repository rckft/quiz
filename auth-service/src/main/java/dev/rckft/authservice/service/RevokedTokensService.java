package dev.rckft.authservice.service;

import dev.rckft.authservice.model.user.RevokedToken;
import dev.rckft.authservice.repository.RevokedTokensRepository;
import dev.rckft.authservice.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RevokedTokensService {

    private final RevokedTokensRepository revokedTokensRepository;
    private final JwtUtil jwtUtil;

    public RevokedTokensService(RevokedTokensRepository revokedTokensRepository, JwtUtil jwtUtil) {
        this.revokedTokensRepository = revokedTokensRepository;
        this.jwtUtil = jwtUtil;
    }

    public void revokeToken(String refreshToken) {
        Date expiration = jwtUtil.getExpiration(refreshToken);
        revokedTokensRepository.save(new RevokedToken(
                jwtUtil.extractJti(refreshToken),
                expiration.toInstant().plusMillis(JwtUtil.ACCESS_TOKEN_DURATION)
        ));
    }

    public boolean isTokenRevoked(String refreshToken) {
        return revokedTokensRepository.findByJti(jwtUtil.extractJti(refreshToken)).isPresent();
    }
}
