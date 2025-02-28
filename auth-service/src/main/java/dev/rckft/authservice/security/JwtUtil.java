package dev.rckft.authservice.security;

import dev.rckft.authservice.controllers.response.AuthTokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static io.jsonwebtoken.Jwts.SIG.*;
import static java.lang.System.*;


@Component
public class JwtUtil {

    private static final SecretKeySpec SECRET_KEY = new SecretKeySpec(
            "very_long_secret_key_at_least_32_bytes_for_HS_256_alg".getBytes(),
            "HmacSHA256"
    );

    private static final long ACCESS_TOKEN_DURATION = Duration.ofMinutes(15).toMillis();
    private static final long REFRESH_TOKEN_DURATION = Duration.ofDays(30).toMillis();
    private static final String JTI = "jti";

    public AuthTokens generateTokens(String userName) {
        String jti = UUID.randomUUID().toString();
        return new AuthTokens(
                generateAccessToken(userName, jti),
                generateRefreshToken(userName, jti)
        );
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public AuthTokens refreshAccessToken(String refreshToken) {
        return new AuthTokens(
                generateAccessToken(extractUsername(refreshToken), extractJti(refreshToken)),
                refreshToken
        );
    }

    public boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private String extractJti(String token) {
        return extractClaims(token).get(JTI, String.class);
    }

    protected Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

    private String generateAccessToken(String userName, String jti) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(currentTimeMillis() + ACCESS_TOKEN_DURATION))
                .claim(JTI, jti)
                .signWith(SECRET_KEY, HS256)
                .compact();
    }

    private String generateRefreshToken(String userName, String jti) {
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(currentTimeMillis() + REFRESH_TOKEN_DURATION))
                .claim(JTI, jti)
                .signWith(SECRET_KEY, HS256)
                .compact();
    }
}
