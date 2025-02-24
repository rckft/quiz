package dev.rckft.authservice.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static io.jsonwebtoken.Jwts.SIG.*;
import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.*;


@Component
public class JwtUtil {

    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec(
            "very_long_secret_key_at_least_32_bytes_for_HS_256_alg".getBytes(),
            "HmacSHA256"
    );

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(currentTimeMillis() + HOURS.toMillis(10)))
                .signWith(SECRET_KEY, HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }
}
