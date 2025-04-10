package dev.rckft.authservice.security;

import dev.rckft.authservice.controllers.response.AuthTokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static io.jsonwebtoken.Jwts.SIG.*;


@Component
public class JwtUtil {

    private final SecretKeySpec secretKeySpec;
    private final Clock clock;

    public static final long ACCESS_TOKEN_DURATION = Duration.ofMinutes(15).toMillis();
    private static final long REFRESH_TOKEN_DURATION = Duration.ofDays(30).toMillis();
    private static final String JTI = "jti";

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey, Clock clock) {
        this.secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        this.clock = clock;
    }

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

    public String extractJti(String token) {
        return extractClaims(token).get(JTI, String.class);
    }

    protected Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(Date.from(clock.instant()));
    }

    public Date getExpiration(String token) {
        return Jwts.parser()
                .verifyWith(secretKeySpec)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private String generateAccessToken(String userName, String jti) {
        Instant now = clock.instant();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(ACCESS_TOKEN_DURATION)))
                .claim(JTI, jti)
                .signWith(secretKeySpec, HS256)
                .compact();
    }

    private String generateRefreshToken(String userName, String jti) {
        Instant now = clock.instant();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(REFRESH_TOKEN_DURATION)))
                .claim(JTI, jti)
                .signWith(secretKeySpec, HS256)
                .compact();
    }
}
