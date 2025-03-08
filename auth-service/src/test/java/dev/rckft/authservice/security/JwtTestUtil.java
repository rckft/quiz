package dev.rckft.authservice.security;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class JwtTestUtil {

    private final JwtUtil jwtUtil;

    public JwtTestUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Claims getClaims(String token) {
        return jwtUtil.extractClaims(token);
    }
}
