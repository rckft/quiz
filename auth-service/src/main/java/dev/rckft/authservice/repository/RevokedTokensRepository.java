package dev.rckft.authservice.repository;

import dev.rckft.authservice.model.user.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RevokedTokensRepository extends JpaRepository<RevokedToken, String> {

    Optional<RevokedToken> findByJti(String jti);

    int deleteAllByExpiryDateBefore(Instant now);
}
