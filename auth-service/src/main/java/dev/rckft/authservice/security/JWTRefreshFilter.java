package dev.rckft.authservice.security;

import dev.rckft.authservice.exception.InvalidTokenException;
import dev.rckft.authservice.service.RevokedTokensService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTRefreshFilter extends OncePerRequestFilter {

    private final RevokedTokensService revokedTokensService;
    private final JwtUtil jwtUtil;

    public JWTRefreshFilter(RevokedTokensService revokedTokensService, JwtUtil jwtUtil) {
        this.revokedTokensService = revokedTokensService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = request.getHeader("X-Refresh-Token");

        try {
            if (isTokenInvalid(refreshToken)) {
                throw new InvalidTokenException();
            }
            doFilter(request, response, filterChain);
        } catch (InvalidTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        }
    }

    private boolean isTokenInvalid(String refreshToken) {
        return refreshToken == null || jwtUtil.isTokenExpired(refreshToken) || isTokenRevoked(refreshToken);
    }

    private boolean isTokenRevoked(String refreshToken) {
        return revokedTokensService.isTokenRevoked(refreshToken);
    }
}
