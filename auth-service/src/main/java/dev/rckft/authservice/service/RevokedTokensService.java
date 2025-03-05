package dev.rckft.authservice.service;

import dev.rckft.authservice.repository.RevokedTokensRepository;
import dev.rckft.authservice.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RevokedTokensService {

    private final RevokedTokensRepository revokedTokensRepository;
    private final JwtUtil jwtUtil;

    public RevokedTokensService(RevokedTokensRepository revokedTokensRepository, JwtUtil jwtUtil) {
        this.revokedTokensRepository = revokedTokensRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void revokeToken(String refreshToken) {
        String jti = jwtUtil.extractJti(refreshToken);
        Date expirationDate = jwtUtil.getExpiration(refreshToken);

        revokedTokensRepository.saveToken(jti, expirationDate);
    }

    public boolean isTokenRevoked(String refreshToken) {
        return revokedTokensRepository.findByJti(jwtUtil.extractJti(refreshToken)).isPresent();
    }
}
